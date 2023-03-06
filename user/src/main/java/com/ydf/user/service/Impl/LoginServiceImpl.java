package com.ydf.user.service.Impl;

import com.ydf.common.util.JwtUtil;
import com.ydf.user.domain.LoginSysUser;
import com.ydf.common.domain.SysUser;
import com.ydf.common.model.Result;
import com.ydf.common.util.RedisCache;
import com.ydf.common.util.ResultUtils;
import com.ydf.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;

@Transactional
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
//    @Autowired
//    private WxUserService wxUserService;

    @Override
    public Result loginSysUser(SysUser sysUser) {
        //AuthenticationManager 进行用户验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getAccount(),sysUser.getPassword());
        //调用UserDetailServiceImpl 进行用户校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userId 生成token
        LoginSysUser loginSysUser = (LoginSysUser) authenticate.getPrincipal();
        String sysUserId = loginSysUser.getSysUser().getId().toString();
        String jwt = JwtUtil.createJWT(sysUserId);
        //authenticate 存入redis
        redisCache.setCacheObject("login:"+sysUserId,loginSysUser);
        //将token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResultUtils.successWithData(200,"登录成功",map);
    }

    @Override
    public Result logout() {
        //获取SecurityContextHolder中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginSysUser loginSysUser = (LoginSysUser) authentication.getPrincipal();
        String sysUserId = loginSysUser.getSysUser().getId().toString();
        //删除redis中的值
        redisCache.deleteObject("login:"+sysUserId);
        return ResultUtils.successWithStatus(200,"退出登录成功");
    }

//    @Override
//    public Result loginWxUser(String code,String rawData,String signature) {
//        // 用户非敏感信息：rawData
//        // 签名：signature
//        JSONObject rawDataJson = JSON.parseObject(rawData);
//        // 1.接收小程序发送的code
//        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
//        JSONObject SessionKeyOpenId = WeChatUtils.getSessionKeyOrOpenId(code);
//        // 3.接收微信接口服务 获取返回的参数
//        String openid = SessionKeyOpenId.getString("openid");
//        String sessionKey = SessionKeyOpenId.getString("session_key");
//
//        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
//        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
//        if (!signature.equals(signature2)) {
//            return ResultUtils.failureWithStatus(500,"签名校验失败");
//        }
//        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；
//        LambdaQueryWrapper<WxUser> lqw = Wrappers.lambdaQuery();
//        lqw.eq(WxUser::getOpenid, openid);
//        WxUser wxUser = wxUserService.getOne(lqw);
//        if (wxUser == null) {
//            wxUser = new WxUser();
//            wxUser.setOpenid(openid);
//            wxUserService.save(wxUser);
//        }
//        String jwt = JwtUtils.createJWT(wxUser.getOpenid());
//        redisCache.setCacheObject("login:"+wxUser.getOpenid(),wxUser);
//        //将token响应给前端
//        HashMap<String,String> map = new HashMap<>();
//        map.put("token",jwt);
//        return ResultUtils.successWithData(200,"登录成功",map);
//    }

}
