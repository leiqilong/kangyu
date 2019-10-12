package com.hlife.server.program.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlife.framework.util.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 处方实体类
 */
@Data
@Accessors(chain = true)
@Document(collection = "prescription")
public class Prescription implements Serializable {

    /**
     * 主键
     */
    @Id
    @Field(value = "id")
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 类别
     * 04 处方
     * 99 其它
     */
    private String type;

    /**
     * 附件list
     */
    private List<MyFile> fileList;

    /**
     * 标签
     */
    @Transient
    private List<String> customFormTags;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.DATE_FMT, timezone = DateUtil.TIME_ZONE)
    private Date createTime;
}
