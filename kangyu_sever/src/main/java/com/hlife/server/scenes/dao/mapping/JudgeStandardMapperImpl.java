package com.hlife.server.scenes.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.server.scenes.dao.JudgeStandardMapper;
import com.hlife.server.scenes.model.JudgeStandard;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 场景设备判别持久层实现
 */
@Repository
public class JudgeStandardMapperImpl extends BaseMapper implements JudgeStandardMapper {

    @Override
    public JudgeStandard saveJudgeStandard(JudgeStandard judgeStandard) {
        return this.mongoTemplate.save(judgeStandard);
    }

    @Override
    public List<JudgeStandard> searchJudgeStandardList(String deviceOfScenesId) {
        return this.mongoTemplate.find(new BasicQuery(new Document("deviceOfScenesId", deviceOfScenesId)), JudgeStandard.class);
    }

    @Override
    public Long deleteJudgeStandardByJudgeStandardId(String judgeStandardId) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("judgeStandardId", judgeStandardId)), JudgeStandard.class).getDeletedCount();
    }

    @Override
    public boolean isExists(Document queryDoc) {
        return this.mongoTemplate.exists(new BasicQuery(queryDoc), JudgeStandard.class);
    }

    @Override
    public Long deleteJudgeStandardByDeviceOfScenesId(String deviceOfScenesId) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("deviceOfScenesId", deviceOfScenesId)), JudgeStandard.class).getDeletedCount();
    }

    @Override
    public void update(Document queryDoc, Update update) {
        this.mongoTemplate.updateMulti(new BasicQuery(queryDoc), update, JudgeStandard.class);
    }
}
