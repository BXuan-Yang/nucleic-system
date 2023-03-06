package com.ydf.point.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydf.common.domain.CheckPoint;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author pacifica
 */
@Mapper
public interface PointMapper extends BaseMapper<CheckPoint> {

    @Insert("insert into check_point (name,longitude,latitude,start_time,end_time,queue_number,doctor_id) values (#{name},#{longitude},#{latitude},#{startTime},#{endTime},#{queueNumber},#{doctorId})")
    int createCheckPoint(String name,
                         Double longitude,
                         Double latitude,
                         Date startTime,
                         Date endTime,
                         Integer queueNumber,
                         Integer doctorId);

    List<CheckPoint> findByName(@Param("nameList") List<String> name);
}
