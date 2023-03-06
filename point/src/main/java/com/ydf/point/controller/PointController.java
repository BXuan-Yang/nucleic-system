package com.ydf.point.controller;

import com.ydf.common.domain.CheckPoint;
import com.ydf.common.model.Result;
import com.ydf.point.exception.IllegalPointException;
import com.ydf.point.service.PointService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author pacifica
 */
@RestController
@RequestMapping("/point")
public class PointController {
    PointService pointService;
    public PointController(PointService pointService){
        this.pointService = pointService;
    }
    /**
     * @Description 新增核酸点
     * @return com.ydf.common.model.Result
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('createPoint')")
    public Result createPoint(@RequestBody CheckPoint checkPoint,@RequestHeader("token") String token){
        checkLongitudeAndLatitude(checkPoint.getLongitude(),checkPoint.getLatitude());
        return pointService.createCheckPoint(checkPoint, token);
    }
    /**
     * @Description     给定经纬度查找附近的核酸点
     * @param longitude 经度
     * @param latitude  纬度
     * @return com.ydf.common.model.Result
     */
    @GetMapping("/find")
    public Result findNearbyPoint(Double longitude,Double latitude){
        checkLongitudeAndLatitude(longitude,latitude);
        return pointService.findNearbyCheckPoint(longitude,latitude);
    }
    public void checkLongitudeAndLatitude(Double longitude,Double latitude){
        boolean legalLongitude = longitude >= -180 && longitude <= 180;
        boolean legalLatitude = latitude >= -90 && latitude <= 90;
        if(!legalLongitude){
            throw new IllegalPointException("经度(longitude)数据值不符合范围");
        }
        if(!legalLatitude){
            throw new IllegalPointException("纬度(latitude)数据值不符合范围");
        }
    }
}
