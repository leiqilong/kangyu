package com.hlife.server.scale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * SNAP_IV 结果实体类
 */
@Data
@Accessors(chain = true)
@Document(collection = "snap_iv")
public class SnapIv implements Serializable {

    /**
     * 主键
     */
    @Indexed(unique = true)
    private String snapId;

    /**
     * 患者guid
     */
    private String guid;

    /**
     * 分数型结果
     */
    private List<Record> score;

    /**
     * 数量型结果
     */
    private List<Record> quantity;

    /**
     * 医生建议
     */
    private String suggestion;

    /**
     * 医生
     */
    private String doctor;

    /**
     * 年龄
     */
    private String age;

    /**
     * 检查时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Data
    @Accessors(chain = true)
    public static class Record implements Serializable  {

        /**
         * 分量表称
         */
        private String title;

        /**
         * 总分/得分>=2的项目数
         */
        private Integer sc;

        /**
         * 平均分
         */
        private Double av;

        /**
         * 评价/判断
         */
        private String ev;
     }
}
