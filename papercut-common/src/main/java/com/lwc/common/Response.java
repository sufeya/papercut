package com.lwc.common;

import lombok.Data;

/**
 * @author liuweichun
 * @date 2022/8/4 21:24
 * 响应公共类
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
public class Response<T> {
    private T data;
    private String code;
    private String msg;


}
