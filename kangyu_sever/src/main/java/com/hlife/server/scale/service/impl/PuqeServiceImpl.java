package com.hlife.server.scale.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.DateUtil;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.outinterface.component.ApiComponent;
import com.hlife.server.outinterface.entity.EventData;
import com.hlife.server.core.model.Record;
import com.hlife.server.core.service.RecordService;
import com.hlife.server.scale.dao.PuqeMapper;
import com.hlife.server.scale.model.Puqe;
import com.hlife.server.scale.service.PuqeService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 妊娠期恶心呕吐量表结果服务层实现
 */
@Service
public class PuqeServiceImpl implements PuqeService {

    @Autowired
    private PuqeMapper pugeMapper;
    @Autowired
    private ApiComponent apiComponent;
    @Autowired
    private RecordService recordService;

    @Override
    public Puqe savePuqe(Puqe puqe) {
        if (StringUtil.stringIsNull(puqe.getPuqeId())) {
            puqe.setPuqeId(GuidUtil.generateGuid())
                    .setCreateTime(new Date());
        }
        puqe = this.pugeMapper.savePuqe(puqe);

        this.savePuqeRemote(puqe);

        return puqe;
    }

    @Override
    public PageResult<Puqe> findPuqePagination(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNull(guid)) {
            throw new RuntimeException("传入患者guid为空");
        }

        queryDoc.put("guid", guid);

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.pugeMapper.findPuqePagination(queryDoc, pageParam);
    }



    private void savePuqeRemote(Puqe puqe) {
        new Thread(() -> {
            Record record = recordService.findOne(puqe.getGuid());
            if (record == null) {
                return;
            }
            EventData<Object> puqeEventData = new EventData<>()
                    .setHzguid(record.getGuid())
                    .setWeixinid(record.getWeixinid())
                    .setDevicType(EventData.DevicType.PUQE_SCALE.getType())
                    .setBgdname(EventData.DevicType.PUQE_SCALE.getName())
                    .setBgdate(DateUtil.getTodayStr(DateUtil.DATE_TIME_FMT))
                    .setDataGuid(puqe.getPuqeId())
                    .setData(puqe);
            apiComponent.devicDataPost(puqeEventData);
        }).start();
    }
}
