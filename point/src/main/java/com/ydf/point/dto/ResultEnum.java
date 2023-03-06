package com.ydf.point.dto;

/**
 * @author Pacifica.
 */
public enum ResultEnum {
    /**
     * 新增核酸点成功的响应信息
    **/
    SUCCEEDED_CREATE(200,"新增核酸点成功"),
    /**
     * 新增核酸点失败的响应信息
    **/
    FAILED_CREATE(500,"新增核酸点失败"),
    /**
     * 查找核酸点成功的响应信息
    **/
    SUCCEEDED_FIND(200,"查找附近核酸点成功"),
    /**
     * 查无符合条件的核酸点的响应信息
    **/
    POINT_NOTFOUND(200,"没有找到符合条件的核酸点");
    private final Integer status;
    private final String msg;

    ResultEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    public Integer getStatus() {
        return status;
    }
    public String getMsg() {
        return msg;
    }

}
