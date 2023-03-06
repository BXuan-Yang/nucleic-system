package com.ydf.common.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckPoint implements Serializable {
    private static final long serialVersionUID = 1430633339880116031L;
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger id;
    private String name;
    private Double longitude;
    private Double latitude;
    private Date startTime;
    private Date endTime;
    private Integer queueNumber;
    private Integer doctorId;
    private Boolean isDeleted;
    private Date createTime;
    private Date updateTime;
}
