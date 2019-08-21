package com.hlife.server.scale.model;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 身高体重测量结果
 */
@Data
@Accessors(chain = true)
@Document("height_and_weight")
public class HeightAndWeight implements Serializable {

    /**
     * 主键
     */
    @Indexed(unique = true)
    private String hwId;

    /**
     * 患者guid
     */
    @Indexed
    private String guid;

    /**
     * 体高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 身高增长
     */
    private Double heightIncrease;

    /**
     * 体重境长
     */
    private Double weightIncrease;

    /**
     * 身高评价
     */
    private String heightEvaluation;

    /**
     * 体重评价
     */
    private String weightEvaluation;

    /**
     * 身高修正评价
     */
    private String heightCorrectEvaluation;

    /**
     * 体重修正评价
     */
    private String weightCorrectEvaluation;

    /**
     * 医生建议
     */
    private String suggestion;

    /**
     * 月龄
     */
    private Double monthAge;

    /**
     * 月龄 整月
     */
    private Integer monthAgeInt;

    /**
     * 校正后月龄
     */
    private Double correctMonthAge;
    // private Double correctMonthAge;

    private String age;

    private String doctor;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    /**
     * 设置身高增长
     * @param lastHeight 上次身高
     * @return 当前对象
     */
    public HeightAndWeight putHeightIncrease(Double lastHeight) {
        if (this.height == null || lastHeight == null) {
            return this;
        }
        this.heightIncrease = this.height - lastHeight;
        return this;
    }

    /**
     * 设置体重增长
     * @param lastWeight 上次体重
     * @return 当前对象
     */
    public HeightAndWeight putWeightIncrease(Double lastWeight) {
        if (this.weight == null || lastWeight == null) {
            return this;
        }
        this.weightIncrease = this.weight - lastWeight;
        return this;
    }

    public JSONArray getMonthHeightEntry() {
        return new JSONArray().fluentAdd(this.monthAge).fluentAdd(this.height);
    }

    public JSONArray getMonthWeightEntry() {
        return new JSONArray().fluentAdd(this.monthAge).fluentAdd(this.weight);
    }

    public JSONArray getCorrectMonthHeightEntry() {
        return new JSONArray().fluentAdd(this.correctMonthAge).fluentAdd(this.height);
    }

    public JSONArray getCorrectMonthWeightEntry() {
        return new JSONArray().fluentAdd(this.correctMonthAge).fluentAdd(this.weight);
    }


    private final static String MONTH_VALUE_ENTRY_FORMAT = "[%f,%f]";

    public JSONArray getMonthIntHeightEntry() {
        return new JSONArray().fluentAdd(this.monthAgeInt).fluentAdd(this.height);
    }

    public JSONArray getMonthIntWeightEntry() {
        return new JSONArray().fluentAdd(this.monthAgeInt).fluentAdd(this.weight);
    }
}