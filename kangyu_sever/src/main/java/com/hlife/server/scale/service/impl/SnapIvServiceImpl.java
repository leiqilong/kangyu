package com.hlife.server.scale.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.core.model.Record;
import com.hlife.server.core.service.RecordService;
import com.hlife.server.scale.dao.SnapIvMapper;
import com.hlife.server.scale.model.SnapIv;
import com.hlife.server.scale.service.SnapIvService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * NAP_IV 服务层实现
 */
@Service
public class SnapIvServiceImpl implements SnapIvService {

    @Autowired
    private SnapIvMapper snapIvMapper;
    @Autowired
    private RecordService recordService;
    @Autowired
    private WeChatComponent weChatComponent;

    @Override
    public SnapIv saveOrEditSnapIv(SnapIv snapIv) {
        if (StringUtil.stringIsNull(snapIv.getGuid())) {
            throw new IllegalArgumentException("患者guid为空");
        }

        if (StringUtil.stringIsNull(snapIv.getSnapId())) {
            snapIv.setSnapId(GuidUtil.generateGuid())
                    .setCreateTime(new Date());
        } else {
            Document queryDoc = new Document("snapId", snapIv.getSnapId());
            SnapIv record = this.snapIvMapper.findOneSnapIv(queryDoc);

            if (record == null) {
                throw new RuntimeException("该数据不存在");
            }

            snapIv.setCreateTime(record.getCreateTime());

            this.snapIvMapper.deleteSnapIv(queryDoc);
        }

        SnapIv si = this.snapIvMapper.saveSnapIv(snapIv);

        new Thread(() -> this.pushMessage(si)).start();

        return si;
    }

    @Override
    public List<SnapIv> findSnapIvHistory(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNotNull(guid)) {
            queryDoc.put("guid", guid);
        }
        return this.snapIvMapper.findSnapIvHistory(queryDoc);
    }

    @Override
    public PageResult<SnapIv> findSnapIvHistoryPagination(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNotNull(guid)) {
            queryDoc.put("guid", guid);
        }

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.snapIvMapper.findSnapIvHistoryPagination(queryDoc, pageParam);
    }

    private void pushMessage(SnapIv si) {
        Record record = this.recordService.findOne(si.getGuid());
        if (record == null || StringUtil.stringIsNull(record.getWeixinid())) {
            return;
        }

        List<SnapIv.Record> quantityList = si.getQuantity();
        if (quantityList != null && quantityList.size() > 0) {
            for (SnapIv.Record re : quantityList) {
                weChatComponent.putMessage(record, new JSONObject().fluentPut("tagName", "SNAP-IV" + re.getTitle()).fluentPut("tagValue", re.getEv()));
            }
        }

        List<SnapIv.Record> scoreList = si.getScore();

        if (scoreList != null && scoreList.size() > 0) {
            for (SnapIv.Record sc : scoreList) {
                weChatComponent.putMessage(record, new JSONObject().fluentPut("tagName", "SNAP-IV" + sc.getTitle()).fluentPut("tagValue", sc.getEv()));
            }
        }
    }
}
