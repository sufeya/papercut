package com.lwc.annotation;

import java.lang.annotation.*;

/**
 * @author liuweichun
 * @date 2022/8/6 21:50
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface WebLogs {
    String describe() default "";
}
