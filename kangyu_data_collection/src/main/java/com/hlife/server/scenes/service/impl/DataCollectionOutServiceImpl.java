package com.hlife.server.scenes.service.impl;

import com.hlife.server.scenes.dao.DataCollectionOutMapper;
import com.hlife.server.scenes.model.DataCollectionOut;
import com.hlife.server.scenes.service.DataCollectionOutService;
import com.hlife.framework.util.StringUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

/**
 * 数据采集业务层实现
 */
@Service
public class DataCollectionOutServiceImpl implements DataCollectionOutService {
    @Autowired
    private DataCollectionOutMapper dataCollectionOutMapper;

    @Override
    public <T> DataCollectionOut<T> saveOrUpdateDataCollectionOut(DataCollectionOut<T> dataCollectionOut) {
        checkDataCollectionOut(dataCollectionOut);
        DataCollectionOut dataOut = dataCollectionOutMapper.findOneDataCollectionOut(getCriteria(dataCollectionOut));
        if (dataOut != null && dataCollectionOutMapper.deleteById(dataOut.getId()) == 0) {
            throw new RuntimeException("更新数据失败");
        }
        if (StringUtil.stringIsNull(dataCollectionOut.getJoinGuid())) {
            dataCollectionOut.setJoinGuid(
                    String.format(
                            DataCollectionOut.joinGuidFormat,
                            dataCollectionOut.getGuid(),
                            dataCollectionOut.getDevicType(),
                            dataCollectionOut.getDataGuid()
                    )
            );
        }
        return dataCollectionOutMapper.saveDataCollectionOut(dataCollectionOut);
    }

    @Override
    public String deleteDataCollectionOutById(String id) {
        if (StringUtil.stringIsNull(id)) {
            throw new IllegalArgumentException("数据id不能为空");
        }
        DataCollectionOut dataOut = dataCollectionOutMapper.findOneDataCollectionOut(Criteria.where("_id").is(new ObjectId(id)));
        if (dataOut == null) {
            throw new RuntimeException("不存在该数据");
        }

        if (dataCollectionOutMapper.deleteById(dataOut.getId()) == 0) {
            throw new RuntimeException("删除数据失败");
        }
        return id;
    }

    /**
     * 检查参数必传参数
     * @param dataCollectionOut 外部数据结构
     */
    private void checkDataCollectionOut(DataCollectionOut dataCollectionOut) {
        if (StringUtil.stringIsNull(dataCollectionOut.getGuid())) {
            throw new IllegalArgumentException("患者guid(guid)不能为空");
        }
        if (StringUtil.stringIsNull(dataCollectionOut.getDevicType())) {
            throw new IllegalArgumentException("数据类型(devicType)不能为空");
        }
        if (StringUtil.stringIsNull(dataCollectionOut.getDataGuid())) {
            throw new IllegalArgumentException("数据guid(dataGuid)不能为空");
        }
    }

    /**
     * 获取查询条件
     * @param dataCollectionOut
     * @return
     */
    private Criteria getCriteria(DataCollectionOut dataCollectionOut) {
        Criteria criteria = new Criteria();
        if (StringUtil.stringIsNotNull(dataCollectionOut.getGuid())) {
            criteria.and("guid").is(dataCollectionOut.getGuid());
        }
        if (StringUtil.stringIsNotNull(dataCollectionOut.getDevicType())) {
            criteria.and("devicType").is(dataCollectionOut.getDevicType());
        }
        if (StringUtil.stringIsNotNull(dataCollectionOut.getDataGuid())) {
            criteria.and("dataguid").is(dataCollectionOut.getDataGuid());
        }
        return criteria;
    }
}
