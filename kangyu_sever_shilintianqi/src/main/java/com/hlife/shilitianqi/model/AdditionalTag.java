package com.hlife.shilitianqi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 附加标签实体类
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "additional_tag")
public class AdditionalTag implements Serializable {

    /**
     * 主标签id
     */
    String mainTagId;

    /**
     * 副标签(附加标签)id
     */
    String assistantTagId;
}

