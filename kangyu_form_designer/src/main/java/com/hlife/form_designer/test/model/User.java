package com.hlife.form_designer.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor

@Document(collection="user")
public class User implements Serializable {
    @Id
    private String id;

    private String name;

    public User(String name) {
        this(UUID.randomUUID().toString(),  name);
    }
}
