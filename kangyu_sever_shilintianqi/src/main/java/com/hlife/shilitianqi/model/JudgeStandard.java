package com.hlife.shilitianqi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 判别
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "judge_standard")
public class JudgeStandard {

    /**
     * 主键
     */
    @Indexed(unique = true)
    private String judgeStandardId;

    /**
     * 场景设备主键
     */
    @Indexed
    private String deviceOfScenesId;

    /**
     * 规则表达式
     */
    private String ruler;

    /**
     * 标签
     */
    private String tagName;

    /**
     * 标签值
     */
    private String tagValue;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 分数
     * <p>
     * 一般 <br/>
     * 正常 0 <br/>
     * 不正常 100 <br/>
     */
    private double score;
}
