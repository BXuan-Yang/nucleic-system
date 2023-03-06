package com.ydf.common.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1430633339880116031L;
    @TableId
    private Integer id;
    private String name;
    private String roleKey;
    private Boolean isDeleted;
    private Date createTime;
    private Date updateTime;
}
