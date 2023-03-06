package com.ydf.user.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydf.common.util.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

public class WeChatUtils {
    public static JSONObject getSessionKeyOrOpenId(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", "wx74a360bc49053db1");
        //小程序secret
        requestUrlParam.put("secret", "c6acf0c10f2f599912ada58c81b32171");
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject(HttpClientUtils.doPost(requestUrl, requestUrlParam));
        return jsonObject;
    }
}
