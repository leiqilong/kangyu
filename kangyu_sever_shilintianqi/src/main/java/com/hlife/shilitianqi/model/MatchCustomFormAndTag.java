package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "match_customform_and_tag")
public class MatchCustomFormAndTag {
    private String customFormId;

    private String tagId;
}
