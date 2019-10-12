package com.hlife.server.program.dao;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.program.model.Prescription;
import org.bson.Document;

import java.util.List;

/**
 * 文件上传下载持久层接口
 */
public interface PrescriptionMapper {


    PageResult<Prescription> findPrescriptionPageResult(Document queryDoc, PageParam pageParam);
    /**
     * 保存
     *
     * @param prescription 处方实例
     * @return 保存完成的处方实例
     */
    Prescription save(Prescription prescription);

    /**
     * 查询处方list
     *
     * @param queryDoc 查询条伯
     *
     * @return 处方list
     */
    List<Prescription> findPrescriptionList(Document queryDoc);

    List<Prescription> findPrescriptionArray(JSONObject jsonObject);

    /**
     * 查询处方是否存在
     *
     * @param id 处方尖
     * @return 处方是否存在
     */
    boolean isExist(String id);

    /**
     * 删除
     *
     * @param id 当前id
     * @return 删除的条数
     */
    long delete(String id);

    Prescription findOne(String id);
}
