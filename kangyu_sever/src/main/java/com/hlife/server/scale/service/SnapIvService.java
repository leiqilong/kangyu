package com.hlife.server.scale.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.SnapIv;

import java.util.List;

/**
 * SNAP_IV 服务层接口
 */
public interface SnapIvService {

    SnapIv saveOrEditSnapIv(SnapIv snapIv);

    List<SnapIv> findSnapIvHistory(JSONObject jsonObject);

    PageResult<SnapIv> findSnapIvHistoryPagination(JSONObject jsonObject);
}
