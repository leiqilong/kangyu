package com.hlife.server.scale.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.HeightAndWeight;
import org.bson.Document;

import java.util.List;

public interface HeightAndWeightMapper {
    HeightAndWeight findLastHeightAndWeight(String guid, Integer monthAgeInt);

    HeightAndWeight findOne(Document queryDoc);

    long remove(Document queryDoc);

    HeightAndWeight save(HeightAndWeight heightAndWeight);

    PageResult<HeightAndWeight> findHeightAndWeightHistoryPagination(Document queryDoc, PageParam pageParam);

    List<HeightAndWeight> findHeightAndWeightList(Document queryDoc);

    boolean isExists(Document queryDoc);
}
