package com.hlife.server.scale.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 身高体重标准数据
 */
@Data
@Accessors(chain = true)
@Document(collection = "gauge_grow")
public class GaugeGrow implements Serializable {

    /**
     * id唯一标识
     */
    @Field("id")
	private String id;
    /**
     * 年龄
     */
	private String year;
    /**
     * 月龄
     */
	private Double monthofage;
    /**
     * 性别
     */
	private String sex;

	//身高标准差字段
    /**
     * -3SD
     */
	private String sgfusansd;
    /**
     * -2SD
     */
	private String sgfuersd;
    /**
     * -1SD
     */
	private String sgfuyisd;
    /**
     * 中位数
     */
	private String sgmidnum;
    /**
     * +1SD
     */
	private String sgyisd;
    /**
     * +2SD
     */
	private String sgersd;
    /**
     * +3SD
     */
	private String sgsansd;

	//体重标准差字段
    /**
     * -3SD
     */
	private String wtfusansd;
    /**
     * -2SD
     */
	private String wtfuersd;
    /**
     * -1SD
     */
	private String wtfuyisd;
    /**
     * 中位数
     */
	private String wtmidnum;
    /**
     * +1SD
     */
	private String wtyisd;
    /**
     * +2SD
     */
	private String wtersd;
    /**
     * +3SD
     */
	private String wtsansd;

    /**
     * 获取身高等级
     * @param height 身高
     * @return 身高等级
     */
    public String getHeightGender(double height) {
        if (height < Double.parseDouble(sgfuersd)) {
            return "生长迟缓";
        }

        if (height >= Double.parseDouble(sgfuersd) && height < Double.parseDouble(sgfuyisd)) {
            return "中下";
        }

        if (height >= Double.parseDouble(sgfuyisd) && height <= Double.parseDouble(sgyisd)) {
            return "中等";
        }

        if (height > Double.parseDouble(sgyisd) && height <= Double.parseDouble(sgersd)) {
            return "中上";
        }

        return "偏高";
    }

    /**
     * 获取体重等级
     * @param weight 体重
     * @return 体重等级
     */
	public String getWeightGender(double weight) {

        if (weight < Double.parseDouble(wtfuersd)) {
            return "体重低";
        }

        if (weight >= Double.parseDouble(wtfuersd) && weight < Double.parseDouble(wtfuyisd)) {
            return "中下";
        }

        if (weight >= Double.parseDouble(wtfuyisd) && weight <= Double.parseDouble(wtyisd)) {
            return "中等";
        }

        if (weight > Double.parseDouble(wtyisd) && weight <= Double.parseDouble(wtersd)) {
            return "中等";
        }

	    return "偏重";
    }

    /**
     * 获取身高 月龄- -3SD
     *
     * @return 身高 月龄--3SD
     */
    public JSONArray getMonthOfAgeSgfusansdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(sgfusansd);
    }

    /**
     * 获取身高 月龄- -2SD
     *
     * @return 身高 月龄--2SD
     */
    public JSONArray getMonthOfAgeSgfuersdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(sgfuersd);
    }

    /**
     * 获取身高 月龄- -1SD
     *
     * @return 身高 月龄--1SD
     */
    public JSONArray getMonthOfAgeSgfuyisdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(sgfuyisd);
    }

    /**
     * 获取身高 月龄- 中位数
     *
     * @return 月龄- 中位数
     */
    public JSONArray getMonthOfAgeSgmidnumEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(sgmidnum);
    }

    public JSONArray getMonthOfAgeSgyisdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(sgyisd);
    }

    public JSONArray getMonthOfAgeSgersdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(sgersd);
    }

    public JSONArray getMonthOfAgeSgsansdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(sgsansd);
    }

    public JSONArray getMonthOfAgeWtfusansdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(wtfusansd);
    }
    public JSONArray getMonthOfAgeWtfuersdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(wtfuersd);
    }

    public JSONArray getMonthOfAgeWtfuyisdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(wtfuyisd);
    }

    public JSONArray getMonthOfAgeWtmidnumEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(wtmidnum);
    }

    public JSONArray getMonthOfAgeWtyisdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(wtyisd);
    }

    public JSONArray getMonthOfAgeWtersdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(wtersd);
    }

    public JSONArray getMonthOfAgeWtsansdEntry() {
        return new JSONArray().fluentAdd(monthofage).fluentAdd(wtsansd);
    }

    private final static String MONTH_VALUE_ENTRY_FORMAT = "[%s,%s]";
}
