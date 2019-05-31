package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@Document(collection = "custom_form_tag")
public class CustomFormTag {

    /**
     * id
     */
    @Field("id")
    @Indexed(unique = true)
    private String id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签代码
     */
    private String tagCode;

    /**
     * 相关表单
     *
     * 不录入到当前文档
     * 录入标签表单关联表
     */
    @Transient
    private List<String> correspondingForms;

    /**
     * 附加标签
     *
     * 不录入到当前文档
     * 不录入到表单自关联表
     */
    @Transient
    private List<String> additionalTags;

    /**
     * 标签类别
     */
    @Indexed
    private String tagType;

    /**
     * 创建时间
     */
    private Date createTime;
}
