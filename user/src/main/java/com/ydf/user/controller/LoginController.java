package com.ydf.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ydf.common.domain.SysUser;

import com.ydf.common.domain.WXUser;
import com.ydf.common.model.Result;
import com.ydf.common.util.JwtUtil;
import com.ydf.common.util.RedisCache;
import com.ydf.common.util.ResultUtils;
import com.ydf.user.service.LoginService;
import com.ydf.user.service.WxUserService;
import com.ydf.user.util.WeChatUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    // 引入自定义Redis配置类
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private WxUserService wxUserService;

    /**
     * 微信自动登录
     * @param code
     * @param rawData
     * @param signature
     * @return Result
     */
    @PostMapping("/wxUser")
    public Result login(@RequestParam(value = "code", required = false) String code,
                        @RequestParam(value = "rawData", required = false) String rawData,
                        @RequestParam(value = "signature", required = false) String signature){
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject SessionKeyOpenId = WeChatUtils.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            return ResultUtils.failureWithStatus(500,"签名校验失败");
        }
        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；
        LambdaQueryWrapper<WXUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(WXUser::getOpenid, openid);
        WXUser wxUser = wxUserService.getOne(lqw);
        if (wxUser == null) {
            wxUser = new WXUser();
            wxUser.setOpenid(openid);
            wxUserService.save(wxUser);
        }
        String jwt = JwtUtil.createJWT(wxUser.getOpenid());
        redisCache.setCacheObject("login:"+wxUser.getOpenid(),wxUser);
        //将token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResultUtils.successWithData(200,"登录成功",map);
    }

    /**
     * 账号密码登录
     * @param sysUser
     * @return
     */
    @PostMapping("/sysUser")
    public Result login(@RequestBody SysUser sysUser){
        return loginService.loginSysUser(sysUser);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/out")
    public Result logout(){
        return loginService.logout();
    }

}
