package com.hlife.server.core.service.impl;

import com.hlife.server.core.dao.ChildOtherArchivesInfoMapper;
import com.hlife.server.core.model.ChildOther;
import com.hlife.server.core.model.ChildOtherArchivesInfo;
import com.hlife.server.core.service.ChildOtherArchivesInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChildOtherArchivesInfoServiceImpl implements ChildOtherArchivesInfoService {

    @Autowired
    private ChildOtherArchivesInfoMapper childOtherArchivesInfoMapper;

    @Override
    public ChildOtherArchivesInfo findOne(String guid) {
        ChildOtherArchivesInfo childOtherArchivesInfo = this.childOtherArchivesInfoMapper.findOne(guid);
        ChildOther childOther = childOtherArchivesInfo.getInfo();
        log.debug("info ==> {}", childOther);
        return childOtherArchivesInfo;
    }
}
