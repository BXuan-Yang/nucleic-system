package com.ydf.user.service;

import com.ydf.common.domain.SysUser;
import com.ydf.common.model.Result;

public interface LoginService {

    Result loginSysUser(SysUser sysUser);

    Result logout();

    //Result loginWxUser(String code,String rawData,String signature);
}
