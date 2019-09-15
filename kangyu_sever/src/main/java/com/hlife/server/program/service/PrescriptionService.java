package com.hlife.server.program.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.server.program.model.MyFile;
import com.hlife.server.program.model.Prescription;

import java.util.List;

/**
 * 处方管理业务层接口
 */
public interface PrescriptionService {


    PageResult<Prescription> findPrescriptionPageResult(JSONObject jsonObject);
    /**
     * 保存
     *
     * @param prescription 处方实例
     * @return 保存完成的处方实例
     */
    Prescription savePrescription(Prescription prescription);

    /**
     * 获取处方list
     * @param jsonObject <br/>
     *                   idList 处方idList</>
     * @return 处方list
     */
    List<Prescription> findPrescriptionList(JSONObject jsonObject);

    /**
     * 获取处方File list
     * @param jsonObject <br/>
     *                   idList 处方idList</>
     * @return 处方File list
     */
    List<MyFile> findFileList(JSONObject jsonObject);

    /**
     * 获取处方图片 list
     *
     * @param jsonObject <br/>
     *                      idList 处方idList</>
     * @return 处方图片 list
     */
    List<MyFile> findPrescriptionPictureList(JSONObject jsonObject);

    /**
     * 删除 处方
     *
     * @param id 处方id
     * @return 删除的条数
     */
    long deletePrescription(String id);

    /**
     * 处方不存在
     *
     * @param id 处方id
     * @return 处方不存在
     */
    boolean notExist(String id);

    /**
     * 处方存在
     * @param id 处方id
     * @return 处方存在
     */
    boolean isExist(String id);

}
