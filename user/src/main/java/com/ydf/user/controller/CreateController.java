package com.ydf.user.controller;

import com.ydf.common.model.Result;
import com.ydf.common.util.JwtUtil;
import com.ydf.common.util.RedisCache;
import com.ydf.user.domain.LoginSysUser;
import com.ydf.user.service.CreateService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/create")
public class CreateController {

    @Autowired
    private CreateService createService;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/user")
    @PreAuthorize("hasAnyAuthority('createDoctor','createVolunteer')")
    public Result createDoctor(@RequestBody HashMap<String,Integer> hashMap,HttpServletRequest request){
        //获取token
        String token = request.getHeader("token");
        //解析token
        String sysUserId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            sysUserId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + sysUserId;
        LoginSysUser loginSysUser = redisCache.getCacheObject(redisKey);
        return createService.createUser(hashMap.get("number"),loginSysUser);
    }

}
