package com.hlife.server.scenes.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@Document(collection = "tag_type")
public class TagType implements Serializable {

    /**
     * 类别名称
     */
    String label;

    /**
     * 类别代码
     */
    String value;
}
