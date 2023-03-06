package com.ydf.point.exception;

/**
 * @author Pacifica.
 */
public class IllegalPointException extends RuntimeException{
    private final String msg;
    public IllegalPointException(String msg){
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
}
