package com.ydf.point.service;

import com.ydf.common.domain.CheckPoint;
import com.ydf.common.model.Result;
import org.springframework.stereotype.Service;

/**
 * @author pacifica
 */
@Service
public interface PointService {
    Result createCheckPoint(CheckPoint checkPoint, String token);
    Result findNearbyCheckPoint(Double longitude, Double latitude);
}
