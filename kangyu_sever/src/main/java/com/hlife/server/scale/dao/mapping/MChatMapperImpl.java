package com.hlife.server.scale.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.server.scale.dao.MChatMapper;
import com.hlife.server.scale.model.MChat;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * MChat 持久层接口实现
 */
@Repository
public class MChatMapperImpl extends BaseMapper implements MChatMapper {

    @Override
    public MChat saveMChat(MChat mChat) {
        return this.mongoTemplate.save(mChat);
    }

    @Override
    public MChat findOne(Document queryDoc) {
        return this.mongoTemplate.findOne(new BasicQuery(queryDoc), MChat.class);
    }

    @Override
    public Long delete(Document queryDoc) {
        return this.mongoTemplate.remove(new BasicQuery(queryDoc), MChat.class).getDeletedCount();
    }

    @Override
    public List<MChat> findMChatHistory(Document queryDoc) {
        return this.mongoTemplate.find(new BasicQuery(queryDoc), MChat.class);
    }
}
