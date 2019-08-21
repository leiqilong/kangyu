package com.hlife.server.scenes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 自定义表单 与 标签匹配数据实体类
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "match_customform_and_tag")
public class MatchCustomFormAndTag implements Serializable {

    /**
     * 表单（量表） id
     */
    private String customFormId;

    /**
     * 标签 id
     */
    private String tagId;
}
