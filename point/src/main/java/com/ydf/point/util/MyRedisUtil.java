package com.ydf.point.util;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Pacifica.
 */
@Component
public class MyRedisUtil {
    private final RedisTemplate<Object,Object> redisTemplate;

    public MyRedisUtil(RedisTemplate<Object,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public  void setGeo(Object key,double longitudeX,double latitudeY,String member){
        redisTemplate.opsForGeo().add(key, new Point(longitudeX, latitudeY), member);
    }
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getPointNearGeo(Object key, double longitudeX, double latitudeY,double radius){
        return redisTemplate.opsForGeo().radius(key,
                new Circle(new Point(longitudeX, latitudeY), new Distance(radius, Metrics.KILOMETERS)));
    }
}
