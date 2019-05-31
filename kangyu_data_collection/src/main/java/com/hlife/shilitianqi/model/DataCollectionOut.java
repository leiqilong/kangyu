package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 数据采集外部数据结构
 *
 * @param <T> 内部数据
 */
@Data
@Accessors(chain = true)
@Document(collection = "data_collection_out")
public class DataCollectionOut<T> implements Serializable {

    public static final String joinGuidFormat = "%s-%s-%s";

    @Id
    private String id;
    /**
     * 患者guid
     */
    private String guid;

    /**
     * 中文名称、描述
     */
    private String name;

    /**
     * 数据类型（需要定义）
     */
    private String devicType;

    /**
     * 数据测试（仪器测试等）时间
     */
    private Date testTime;

    /**
     * 前台随机生成的唯标识
     */
    private String dataGuid;

    /**
     * 内部数据结构
     */
    private List<T> innerData;

    /**
     * 数据上传时间
     */
    private Date uploadTime;

    /**
     * 拼接guid
     */
    private String joinGuid;

    /**
     * 所属机构（某医院）
     */
    private String organization;
}
