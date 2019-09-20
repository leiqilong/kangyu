package com.hlife.server.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlife.framework.util.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * 患者档案
 */
@Data
@Accessors(chain = true)
@Document(collection = "record")
public class Record implements Serializable {

    /**
     * 主键
     */
    @Indexed(unique = true)
    private String guid;

    /**
     * 角色
     */
    private Integer node;

    /**
     * 卡号
     */
    private String cardId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号/出生证明号
     */
    private String idnumber;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = DateUtil.DATE_FMT, timezone = DateUtil.TIME_ZONE)
    private Date birthday;

    /**
     * 微信id
     */
    @Field(value = "Weixinid")
    private String Weixinid;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.DATE_TIME_FMT, timezone = DateUtil.TIME_ZONE)
    private Date createTime;

    /**
     * 场景
     */
    private String scenesId;

    /**
     * 下次检查日期
     */
    @JsonFormat(pattern = DateUtil.DATE_FMT, timezone = DateUtil.TIME_ZONE)
    private Date nextCheckDate;

    /**
     * 年龄详细
     */
    @Transient
    private DateUtil.Age ageDetail;

    /**
     * 月龄
     */
    /*@Transient
    private Double monthAge;*/

    /**
     * 年龄详细str
     */
    /*@Transient
    private String ageDetailStr;*/
    public Record putAgeDetail() {
        this.ageDetail = DateUtil.getAge(this.birthday);
        /*this.monthAge = this.ageDetail.getMonthAge();
        this.ageDetailStr = this.ageDetail.getAgeDetail();*/
        return this;
    }
}
