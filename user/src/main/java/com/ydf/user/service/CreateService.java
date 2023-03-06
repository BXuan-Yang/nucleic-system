package com.ydf.user.service;

import com.ydf.common.model.Result;
import com.ydf.user.domain.LoginSysUser;

public interface CreateService {

    Result createUser(Integer number, LoginSysUser loginSysUser);

}
