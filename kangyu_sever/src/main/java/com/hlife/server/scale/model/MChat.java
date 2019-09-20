package com.hlife.server.scale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlife.framework.util.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * MChart婴幼儿孤独症筛查量表 结果实体类
 */
@Data
@Accessors(chain = true)
@Document(collection = "m_chart")
public class MChat implements Serializable {

    /**
     * 主键
     */
    @Indexed(unique = true)
    private String chatId;

    /**
     * 患者guid
     */
    @Indexed
    private String guid;

    /**
     * 核心项目为阳性的题目号
     */
    private List<String> positiveCoreItemList;

    /**
     * 核心项目阳性题目数
     */
    private Integer positiveCoreItemTotal;

    /**
     * 普通项目为阴性的题目号
     */
    private List<String> negativeOrdinaryItemList;

    /**
     * 普通项目为阴性的题目数
     */
    private Integer negativeOrdinaryItemTotal;

    /**
     * 阴性题目总数
     */
    private Integer positiveItemTotal;

    /**
     * 结论
     */
    private String result;

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
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.DATE_TIME_FMT, timezone = DateUtil.TIME_ZONE)
    private Date createTime;
}
