package com.hlife.server.scale.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.dao.SnapIvMapper;
import com.hlife.server.scale.model.SnapIv;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * SNAP_IV持久层实现
 */
@Repository
public class SnapIvMapperImpl extends BaseMapper implements SnapIvMapper {

    @Override
    public SnapIv saveSnapIv(SnapIv snapIv) {
        return this.mongoTemplate.save(snapIv);
    }

    @Override
    public SnapIv findOneSnapIv(Document queryDoc) {
        return this.mongoTemplate.findOne(new BasicQuery(queryDoc), SnapIv.class);
    }

    @Override
    public long deleteSnapIv(Document queryDoc) {
        return this.mongoTemplate.remove(new BasicQuery(queryDoc), SnapIv.class).getDeletedCount();
    }

    @Override
    public List<SnapIv> findSnapIvHistory(Document queryDoc) {
        return this.mongoTemplate.find(new BasicQuery(queryDoc), SnapIv.class);
    }

    @Override
    public PageResult<SnapIv> findSnapIvHistoryPagination(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(
                new BasicQuery(queryDoc),
                SnapIv.class,
                Optional.ofNullable(pageParam.getPageSize()).orElse(5),
                Optional.ofNullable(pageParam.getPageNum()).orElse(1),
                Sort.Order.desc("createTime")
        );
    }
}
