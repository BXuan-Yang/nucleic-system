package com.ydf.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUserVo implements Serializable {
    private static final long serialVersionUID = 1430633339880116031L;
    private String account;
    private String password;
}
