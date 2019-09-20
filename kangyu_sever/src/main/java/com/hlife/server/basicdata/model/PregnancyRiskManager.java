package com.hlife.server.basicdata.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlife.framework.base.BaseTree;
import com.hlife.framework.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 孕产妇妊娠风险管理实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Document(collection = "pregnancy_risk_manager")
public class PregnancyRiskManager extends BaseTree implements Serializable {

    /**
     * 唯一标识
     */
    @Indexed(unique = true)
    private String prmId;

    /**
     * 风险名称
     */
    private String name;

    /**
     * 风险等级（标签）
     */
    private String tagId;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签值
     */
    private String tagValue;

    /**
     * 标签状态
     */
    private Boolean state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.DATE_TIME_FMT, timezone = DateUtil.TIME_ZONE)
    private Date createTime;
}
