package com.lwc.config;

/**
 * @author liuweichun
 * @date 2022/8/21 11:27
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public enum ConfigEnum {
    /**
     * 文件上传地址
     */
    FILE_UPLOAD_PATH("imgPath","\\static\\img\\"),
    ALIYUN_OSS_REGION("oss region","cn-hangzhou"),
    PUBLISH_STATUS_SELF("仅个人可见","0"),
    PUBLISH_STATUS_FORKS("关注的人可看","1"),
    PUBLIS_STATUS_PUBLIC("所有人可见","2"),
    LIKE_STATUS("点赞状态","1"),
    ULIKE_STATUS("取消点赞状态","0");

    ConfigEnum(String name ,String path){
        this.name = name;
        this.path = path;
    }
    private String name;
    private String path;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
