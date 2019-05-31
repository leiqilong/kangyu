package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Document(value = "hospital")
public class Hospital implements Serializable {
    /**
     * 唯一标识
     */
    @Field("id")
    private String id;
    /**
     * 医院姓名
     */
    private String hospitalname;
    /**
     * 机构编码
     */
    private String jgcode;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系方式
     */
    private String linktype;
    /**
     * 区域编码
     */
    private String areacode;
    /**
     * 医院地址
     */
    private String hospitaladdress;
}
