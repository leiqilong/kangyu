package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 场景基本信息
 */
@Data
@Accessors(chain = true)
@Document(collection = "scenes")
public class Scenes implements Serializable {

    /**
     * 主键
     */
    @Indexed(unique = true)
    private String scenesId;

    /**
     * 场景名称
     */
    private String scenesName;

    /**
     * 场景代码
     */
    private String scenesCode;
}
