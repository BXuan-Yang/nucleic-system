package com.ydf.common.util;

import com.ydf.common.model.Result;

/**
 * Result生成工具类
 */
public class ResultUtils {

    protected ResultUtils() {}

    public static Result newResult() {
        return new Result();
    }

    public static Result newResult(boolean success) {
        return new Result(success);
    }

    // 业务调用成功
    public static Result success() {
        return new Result();
    }

    public static Result successWithStatus(Integer status) {
        return new Result(true, status);
    }

    public static Result successWithStatus(Integer status, String msg) {
        return new Result(true, status, msg);
    }

    public static Result successWithData(Integer status, String msg, Object data) {
        return new Result(true, status, msg, data);
    }


    // 业务调用失败
    public static Result failure() {
        return new Result(false);
    }

    public static Result failureWithStatus(Integer status) {
        return new Result(false, status);
    }

    public static Result failureWithStatus(Integer status, String msg) {
        return new Result(false, status, msg);
    }

    public static Result failureWithData(Integer status, String msg,Object data) {
        return new Result(false, status, msg, data);
    }


}