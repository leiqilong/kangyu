package com.hlife.shilitianqi.dao;

import com.hlife.shilitianqi.model.DataCollectionOut;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 数据采集持久层接口
 */
public interface DataCollectionOutMapper {

     /**
      * 保存数据采集外部数据
      * @param dataCollectionOut 外部数据结构
      * @param <T> 内部数据泛型
      * @return 采呃成功的外部数据
      */
     <T> DataCollectionOut<T> saveDataCollectionOut(DataCollectionOut<T> dataCollectionOut);

     /**
      * 查询单个外部数据
      * @param criteria 查询条件
      * @param <T> 泛型
      * @return 外部数据
      */
     <T> DataCollectionOut findOneDataCollectionOut(Criteria criteria);

     /**
      * 查询符合条件的总条数
      * @param criteria 查询条件
      * @return 总条数
      */
     long countDataCollectionOut(Criteria criteria);

     /**
      * 修改文本档 未实现
      * @param dataCollectionOut 外部数据结构
      * @param <T> 内部数据泛型
      */
     <T> void updateDataCollectionOut(DataCollectionOut<T> dataCollectionOut);

     /**
      * 根据数据id删除
      * @param id id
      */
     long deleteById(String id);
}
