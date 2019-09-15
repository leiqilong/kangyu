package com.hlife.server.program.model;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文件实体类
 */
@Data
@Accessors(chain = true)
public class MyFile implements Serializable {

    private Long uid;
    /**
     * 文件名
     */
    private String name;

    /**
     * 文件路
     */
    private String path;

    /**
     * 上传状态
     */
    private String status;

    /**
     *
     */
    private String base64Str;

    /**
     * 状态枚举
     */
    public enum Status {
        SUCCESS("success"),

        FINISHED("finished");

        @Getter
        private String name;

        Status(String name) {
            this.name = name;
        }
    }
}
