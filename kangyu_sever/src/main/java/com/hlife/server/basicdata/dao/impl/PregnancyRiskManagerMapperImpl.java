package com.hlife.server.basicdata.dao.impl;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.basicdata.dao.PregnancyRiskManagerMapper;
import com.hlife.server.basicdata.model.PregnancyRiskManager;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 孕产妇妊娠风险管理持久层实现
 */
@Repository
public class PregnancyRiskManagerMapperImpl extends BaseMapper implements PregnancyRiskManagerMapper {

    @Override
    public PregnancyRiskManager savePregnancyRiskManager(PregnancyRiskManager pregnancyRiskManager) {
        return this.mongoTemplate.save(pregnancyRiskManager);
    }

    @Override
    public PregnancyRiskManager findPregnancyRiskManagerByPrmId(String prmId) {
        return this.mongoTemplate.findOne(new Query(where("prmId").is(prmId)), PregnancyRiskManager.class);
    }

    @Override
    public boolean isExits(String prmId) {
        return this.mongoTemplate.exists(new Query(where("prmId").is(prmId)), PregnancyRiskManager.class);
    }

    @Override
    public long deletePregnancyRiskManager(String prmId) {
        return this.mongoTemplate.remove(new Query(where("prmId").is(prmId)), PregnancyRiskManager.class).getDeletedCount();
    }

    @Override
    public boolean isExitsChildren(String prmId) {
        return this.mongoTemplate.exists(new Query(where("parentId").is(prmId)), PregnancyRiskManager.class);
    }

    @Override
    public long editPregnancyRiskManager(PregnancyRiskManager pregnancyRiskManager) {
        Update update = Update.update("nodeId", pregnancyRiskManager.getPrmId());
        if (StringUtil.stringIsNotNull(pregnancyRiskManager.getName())) {
            update.set("name", pregnancyRiskManager.getName());
        }
        if (StringUtil.stringIsNull(pregnancyRiskManager.getParentId())) {
            update.set("parentId", pregnancyRiskManager.getParentId());
        }
        if (StringUtil.stringIsNull(pregnancyRiskManager.getTagId())) {
            update.set("tagId", pregnancyRiskManager.getTagId());
        }
        if (StringUtil.stringIsNull(pregnancyRiskManager.getTagName())) {
            update.set("tagName", pregnancyRiskManager.getTagName());
        }
        if (StringUtil.stringIsNull(pregnancyRiskManager.getTagValue())) {
            update.set("tagValue", pregnancyRiskManager.getTagValue());
        }
        if (Objects.nonNull(pregnancyRiskManager.getState())) {
            update.set("state", pregnancyRiskManager.getState());
        }

        return this.mongoTemplate.updateFirst(new Query(where("prmId").is(pregnancyRiskManager.getPrmId())), update, PregnancyRiskManager.class).getModifiedCount();
    }

    @Override
    public List<PregnancyRiskManager> searchPregnancyRisk(Document document) {
        return this.mongoTemplate.find(new BasicQuery(document), PregnancyRiskManager.class);
    }
}
