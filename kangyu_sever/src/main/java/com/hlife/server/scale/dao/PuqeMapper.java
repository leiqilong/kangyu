package com.hlife.server.scale.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.Puqe;
import org.bson.Document;

/**
 * 妊娠期恶心呕吐量表结果持久层接口
 */
public interface PuqeMapper {


    /**
     * 保存
     * @param puqe 妊娠期恶心呕吐量表结果
     * @return 妊娠期恶心呕吐量表结果
     */
    Puqe savePuqe(Puqe puqe);

    /**
     * 分页查询
     *
     * @param queryDoc guid 串者guid
     * @param pageParam 页参数
     *
     * @return 当前页数据
     */
    PageResult<Puqe> findPuqePagination(Document queryDoc, PageParam pageParam);
}
