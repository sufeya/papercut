package com.lwc.common;

/**
 * @author liuweichun
 * @date 2022/8/6 17:19
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public enum ResultCode implements IErrorCode{
    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private long code;
    private String message;

    ResultCode(long code, String message){
        this.code = code;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public long getCode() {
        return code;
    }
}
