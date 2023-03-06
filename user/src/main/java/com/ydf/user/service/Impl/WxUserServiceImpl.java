package com.ydf.user.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydf.common.domain.WXUser;
import com.ydf.user.mapper.WxUserMapper;
import com.ydf.user.service.WxUserService;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WXUser> implements WxUserService {

}
