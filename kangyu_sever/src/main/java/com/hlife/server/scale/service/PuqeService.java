package com.hlife.server.scale.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.Puqe;

/**
 * 妊娠期恶心呕吐量表结果服务层接口
 */
public interface PuqeService {

    /**
     * 保存 妊娠期恶心呕吐量表结果
     *
     * @param puge 妊娠期恶心呕吐量表结果
     * @return 妊娠期恶心呕吐量表结果
     */
    Puqe savePuqe(Puqe puge);

    /**
     * 分页查询
     *
     * @param jsonObject pagesize 每页大小
     *                   pagenum 页数
     *
     * @return 当前页数据
     */
    PageResult<Puqe> findPuqePagination(JSONObject jsonObject);
}
