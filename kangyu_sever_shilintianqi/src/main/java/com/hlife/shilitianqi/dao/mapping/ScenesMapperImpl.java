package com.hlife.shilitianqi.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.dao.ScenesMapper;
import com.hlife.shilitianqi.model.DeviceOfScenes;
import com.hlife.shilitianqi.model.Scenes;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 场景持久层实现
 */
@Repository
public class ScenesMapperImpl extends BaseMapper implements ScenesMapper {

    @Override
    public Scenes saveScenes(Scenes scenes) {
        return this.mongoTemplate.save(scenes);
    }

    @Override
    public List<Scenes> searchScenesListAll() {
        return this.mongoTemplate.findAll(Scenes.class);
    }

    @Override
    public PageResult<Scenes> searchScenesListByParams(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(new BasicQuery(queryDoc), Scenes.class, pageParam.getPageSize(), pageParam.getPageNum());
    }

    @Override
    public boolean isExists(Document queryDoc) {
        return this.mongoTemplate.exists(new BasicQuery(queryDoc), Scenes.class);
    }

    @Override
    public Long deleteScenesById(String scenesId) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("scenesId", scenesId)), Scenes.class).getDeletedCount();
    }

    @Override
    public Scenes updateScenes(Scenes scenes) {
        Update update = Update.update("scenesCode", scenes.getScenesCode())
                .set("scenesName", scenes.getScenesName());
        this.mongoTemplate.updateFirst(
                new BasicQuery(
                        new Document("scenesId", scenes.getScenesId())
                ),
                update,
                Scenes.class
        );
        return scenes;
    }
}
