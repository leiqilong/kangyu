package com.hlife.server.scale.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.SnapIv;

import java.util.List;

/**
 * SNAP_IV 服务层接口
 */
public interface SnapIvService {

    /**
     * 保存修改 spanIv测量结果
     *
     * @param snapIv spanIv测量结果
     * @return spanIv测量结果
     */
    SnapIv saveOrEditSnapIv(SnapIv snapIv);

    /**
     * 获取 spanIv测量结果历史列表
     *
     * @param jsonObject <br/>
     *                   guid 患者id</>
     *
     * @return spanIv测量结果历史列表
     */
    List<SnapIv> findSnapIvHistory(JSONObject jsonObject);

    /**
     * 获取 spanIv测量结果历史分页
     *
     * @param jsonObject panIv测量结果历史分页
     * @return panIv测量结果历史分页
     */
    PageResult<SnapIv> findSnapIvHistoryPagination(JSONObject jsonObject);
}
