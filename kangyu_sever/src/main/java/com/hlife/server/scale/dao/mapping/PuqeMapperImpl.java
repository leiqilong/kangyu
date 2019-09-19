package com.hlife.server.scale.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.dao.PuqeMapper;
import com.hlife.server.scale.model.Puqe;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 妊娠期恶心呕吐量表结果持久层实现
 */
@Repository
public class PuqeMapperImpl extends BaseMapper implements PuqeMapper {

    @Override
    public Puqe savePuqe(Puqe puqe) {
        return this.mongoTemplate.save(puqe);
    }

    @Override
    public PageResult<Puqe> findPuqePagination(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(
                new BasicQuery(queryDoc),
                Puqe.class,
                Optional.ofNullable(pageParam.getPageSize()).orElse(5),
                Optional.ofNullable(pageParam.getPageNum()).orElse(1),
                Sort.Order.desc("createTime")
        );
    }
}
