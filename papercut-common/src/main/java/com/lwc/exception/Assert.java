package com.lwc.exception;

import com.lwc.common.IErrorCode;

/** 断言处理类，用于抛出各种API异常
 * @author liuweichun
 * @date 2022/8/6 18:47
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class Assert {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
