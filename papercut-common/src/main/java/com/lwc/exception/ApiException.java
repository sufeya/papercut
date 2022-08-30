package com.lwc.exception;

import com.lwc.common.IErrorCode;

/**
 * 异常处理类
 * @author liuweichun
 * @date 2022/8/6 18:20
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class ApiException extends  RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
