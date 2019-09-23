package com.hlife.server.scale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlife.framework.util.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * 孕期风险筛查
 */
@Data
@Accessors(chain = true)
@Document(collection = "pregnancy_risk_screening")
public class PregnancyRiskScreening {

    /**
     * 唯一标识
     */
    @Indexed(unique = true)
    private String prsId;

    /**
     * 患者guid
     */
    @Indexed
    private String guid;

    /**
     * 风险列表
     */
    private List<Risk> tableData;

    /**
     * 筛查结果
     */
    private List<Result> result;

    /**
     * 测量医生
     */
    @Indexed
    private String doctorId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.DATE_TIME_FMT, timezone = DateUtil.TIME_ZONE)
    private Date createTime;

    /**
     * 患者风险类
     */
    @Data
    @Accessors(chain = true)
    private static class Risk {

        /**
         * 风险id
         */
        private String prmId;

        /**
         * 风险名称
         */
        private String name;

        /**
         * 标签id
         */
        private String tagId;

        /**
         * 标签名称
         */
        private String tagName;

        /**
         * 标签值
         */
        private String tagValue;
    }

    /**
     * 筛查结果类
     */
    @Data
    @Accessors(chain = true)
    private static class Result {

        /**
         * 标签id
         */
        @Indexed
        private String id;

        /**
         * 标签名
         */
        private String tagName;

        /**
         * 标签值
         */
        private String tagValue;

        /**
         * 标签说明
         */
        private String tagRemark;

        /**
         * 颜色
         */
        private String color;

        /**
         * 优先级
         */
        private Integer priority;
    }
}
