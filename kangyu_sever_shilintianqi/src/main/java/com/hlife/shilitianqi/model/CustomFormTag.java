package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@Document("custom_form_tag")
public class CustomFormTag {

    @Field("id")
    private String id;

    private String tagName;

    private List<String> correspondingForms;

    private String additionalInformation;

    private Date createTime;
}
