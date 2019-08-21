package com.hlife.server.scenes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 设备实体类
 */
@Data
@Accessors(chain = true)
@Document(collection = "device")
public class Device implements Serializable {

    public static final String X = "X";
    public static final String Y = "Y";
    public static final String Z = "Z";

    /**
     * 调备主键
     */
    @Indexed(unique = true)
    private String deviceId;

    /**
     * 设备代码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 运算类型
     */
    private List<CalculationType> calculationTypeList;

    /**
     * 运算类型实体类
     */
    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CalculationType implements Serializable {

        /**
         * 运算类型名称
         */
        private String typeName;

        /**
         * 算法key
         */
        private String scenesFunKey;
        /**
         *
         */
        private Boolean base;

        /**
         * 参与运算字段
         */
        private FieldPath[] fieldPaths;

        /**
         * 下拉选择项
         */
        private String[] selector;

        public CalculationType(String typeName, String scenesFunKey, FieldPath[] fieldPaths) {
            this(typeName, scenesFunKey, false, fieldPaths, null);
        }
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldPath {

        private String path;

        private String variable;

        public FieldPath(String path) {
            this(path, null);
        }
    }

}
