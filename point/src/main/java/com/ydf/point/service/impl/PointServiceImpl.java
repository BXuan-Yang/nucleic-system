package com.ydf.point.service.impl;

import com.ydf.common.domain.CheckPoint;
import com.ydf.common.model.Result;
import com.ydf.common.util.JwtUtil;
import com.ydf.common.util.ResultUtils;
import com.ydf.point.dto.ResultEnum;
import com.ydf.point.global.RedisKeys;
import com.ydf.point.mapper.PointMapper;
import com.ydf.point.service.PointService;
import com.ydf.point.util.MyRedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author pacifica
 */
@Service
public class PointServiceImpl implements PointService {
    PointMapper pointMapper;
    MyRedisUtil myRedisUtil;
    private static final Integer NEAR_KM = 3;

    public PointServiceImpl(PointMapper pointMapper,MyRedisUtil myRedisUtil) {
        this.pointMapper = pointMapper;
        this.myRedisUtil = myRedisUtil;
    }

    @Override
    @Transactional
    public Result createCheckPoint(CheckPoint checkPoint, String token) {
        String sysUserId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            sysUserId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        int doctorId = Integer.parseInt(sysUserId);
        int createRes = pointMapper.createCheckPoint(checkPoint.getName(),
                checkPoint.getLongitude(),
                checkPoint.getLatitude(),
                checkPoint.getStartTime(),
                checkPoint.getEndTime(),
                checkPoint.getQueueNumber(),
                doctorId);
        if(createRes >= 1){
            myRedisUtil.setGeo(RedisKeys.POINTS_KEY, checkPoint.getLongitude(), checkPoint.getLatitude(), checkPoint.getName());
            return ResultUtils.successWithStatus(ResultEnum.SUCCEEDED_CREATE.getStatus(),ResultEnum.SUCCEEDED_CREATE.getMsg());
        }
        return ResultUtils.failureWithStatus(ResultEnum.FAILED_CREATE.getStatus(),ResultEnum.FAILED_CREATE.getMsg());
    }

    @Override
    public Result findNearbyCheckPoint(Double longitude, Double latitude) {
        GeoResults<RedisGeoCommands.GeoLocation<Object>> nearGeo = myRedisUtil.getPointNearGeo(RedisKeys.POINTS_KEY, longitude, latitude,NEAR_KM);
        if(Objects.isNull(nearGeo) || nearGeo.getContent().size() == 0){
            return ResultUtils.successWithStatus(ResultEnum.POINT_NOTFOUND.getStatus(),ResultEnum.POINT_NOTFOUND.getMsg());
        }
        List<String> pointNamesList = new LinkedList<>();
        nearGeo.forEach(i -> pointNamesList.add((String) i.getContent().getName()));
        List<CheckPoint> pointList = pointMapper.findByName(pointNamesList);
        return ResultUtils.successWithData(ResultEnum.SUCCEEDED_FIND.getStatus(),
                ResultEnum.SUCCEEDED_FIND.getMsg(),
                pointList);
    }
}
