package com.hlife.server.scale.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.SnapIv;
import org.bson.Document;

import java.util.List;

/**
 * SNAP_IV持久层接口
 */
public interface SnapIvMapper {

    SnapIv saveSnapIv(SnapIv snapIv);

    SnapIv findOneSnapIv(Document queryDoc);

    long deleteSnapIv(Document queryDoc);

    List<SnapIv> findSnapIvHistory(Document queryDoc);

    PageResult<SnapIv> findSnapIvHistoryPagination(Document queryDoc, PageParam pageParam);
}
