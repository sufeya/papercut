package com.lwc.config;

/**
 * @author liuweichun
 * @date 2022/8/26 14:52
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public enum  IntegerConfigEnum {
    WORK_LIKE("作品点赞",1),
    WORK_DISLIKE("取消点赞",0);

    private String name;
    private Integer value;

    IntegerConfigEnum(String name,Integer value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
