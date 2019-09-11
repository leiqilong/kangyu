package com.hlife.server.program.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 附件list
     */
    private List<MyFile> fileList;

    /**
     * 标签
     */
    @Transient
    private List<String> customFormTags;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
}