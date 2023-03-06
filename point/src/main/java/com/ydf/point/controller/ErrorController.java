package com.ydf.point.controller;

import com.ydf.common.model.Result;
import com.ydf.common.util.ResultUtils;
import com.ydf.point.exception.IllegalPointException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Pacifica.
 */
@RestControllerAdvice
public class ErrorController {
    @ResponseBody
    @ExceptionHandler({IllegalPointException.class})
    public Result illegalPointExceptionHandler(IllegalPointException illegalPointException){
        return ResultUtils.failureWithStatus(400,illegalPointException.getMsg());
    }
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception exception){
        return ResultUtils.failureWithStatus(500,exception.getMessage());
    }
}
