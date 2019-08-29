package com.hlife.server.scale.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.core.model.Record;
import com.hlife.server.core.service.RecordService;
import com.hlife.server.scale.dao.MChatMapper;
import com.hlife.server.scale.model.MChat;
import com.hlife.server.scale.service.MChatService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * MChat服务层实现
 */
@Service
public class MChatServiceImpl implements MChatService {

    @Autowired
    private MChatMapper mChatMapper;
    @Autowired
    private RecordService recordService;
    @Autowired
    private WeChatComponent weChatComponent;

    @Override
    public MChat saveMChat(MChat mChat) {
        if (StringUtil.stringIsNull(mChat.getGuid())) {
            throw new IllegalArgumentException("患者guid为空！");
        }

        if (StringUtil.stringIsNull(mChat.getChatId())) {
            mChat.setChatId(GuidUtil.generateGuid())
                    .setCreateTime(new Date());
        } else {
            Document queryDoc = new Document("chatId", mChat.getChatId());
            MChat record = this.findOne(queryDoc);
            if (record == null) {
                throw new RuntimeException("该条记录不存在");
            }
            mChat.setCreateTime(record.getCreateTime());
            this.delete(queryDoc);
        }
        MChat mc = mChatMapper.saveMChat(mChat);

        if (StringUtil.stringIsNull(mc.getResult())) {
            return mc;
        }

        new Thread(() ->this.sendMessage(mc)).start();

        return mc;
    }

    @Override
    public MChat findOne(Document queryDoc) {
        return this.mChatMapper.findOne(queryDoc);
    }

    @Override
    public Long delete(Document queryDoc) {
        return this.mChatMapper.delete(queryDoc);
    }

    @Override
    public List<MChat> findMChatHistory(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNotNull(guid)) {
            queryDoc.put("guid", guid);
        }
        return this.mChatMapper.findMChatHistory(queryDoc);
    }


    private void sendMessage(MChat mChat) {
        Record record = this.recordService.findOne(mChat.getGuid());
        if (record == null || StringUtil.stringIsNull(record.getWeixinid())) {
            return;
        }

        String result = mChat.getResult();
        JSONObject tagJSONObject = new JSONObject().fluentPut("tagName", "M-CHAT").fluentPut("tagValue", result.substring(0, 2));

        weChatComponent.putMessage(record, tagJSONObject);
    }
}
