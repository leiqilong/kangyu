package com.hlife.server.scale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlife.framework.util.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

/**
 * 妊娠期恶心呕吐量表结果实体类
 */
@Data
@Accessors(chain = true)
public class Puqe implements Serializable {

    /**
     * 主键
     */
    @Indexed(unique = true)
    private String puqeId;

    /**
     * 患者id
     */
    @Indexed
    private String guid;

    /**
     * 分数列表
     */
    private Integer[] scores;

    /**
     * 总分
     */
    private Integer totalScore;

    /**
     * 标签
     */
    private String tag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.DATE_TIME_FMT, timezone = DateUtil.TIME_ZONE)
    private Date createTime;
}
