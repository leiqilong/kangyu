package com.hlife.form_designer.test.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@Document(collection="student")
public class Student implements Serializable {
    private String id;
    private String name;
}
