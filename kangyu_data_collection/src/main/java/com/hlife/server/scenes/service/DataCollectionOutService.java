package com.hlife.server.scenes.service;

import com.hlife.server.scenes.model.DataCollectionOut;

/**
 * 数据采集业务层接口
 */
public interface DataCollectionOutService {
    /**
     * 数据采集业务层入口
     * <p/><p/>
     * 新增或修改数据
     * @param dataCollectionOut 外层数据结构
     * @param <T> 泛型堑时用不上
     * @return 保存成功的数据结构
     */
    <T> DataCollectionOut<T> saveOrUpdateDataCollectionOut(DataCollectionOut<T> dataCollectionOut);

    String deleteDataCollectionOutById(String id);
}
