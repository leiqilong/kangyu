package com.hlife.server.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public abstract class SubArchivesInfoOut<T> implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 科室
     */
    private Integer node;

    /**
     * 具体内容
     */
    private T info;
}
