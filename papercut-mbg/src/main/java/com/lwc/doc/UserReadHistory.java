package com.lwc.doc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 用户浏览历史记录
 * @author liuweichun
 * @date 2022/8/22 16:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@Document
public class UserReadHistory {
    @Id
    private String id;
    @Indexed
    private String userId;

    private String userName;
    private String userIcon;
    @Indexed
    private String workId;
    private String workName;
    private String workPic;
    private String workTitle;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    // 格式化
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
