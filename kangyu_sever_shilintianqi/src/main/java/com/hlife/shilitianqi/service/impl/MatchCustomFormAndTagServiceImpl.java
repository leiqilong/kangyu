package com.hlife.shilitianqi.service.impl;

import com.hlife.shilitianqi.dao.MatchCustomFormAndTagMapper;
import com.hlife.shilitianqi.service.MatchCustomFormAndTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchCustomFormAndTagServiceImpl implements MatchCustomFormAndTagService {
    @Autowired
    private MatchCustomFormAndTagMapper matchCustomFormAndTagMapper;

    @Override
    public long deleteMatchCustomFormAndTagByTagId(String tagId) {
        return matchCustomFormAndTagMapper.deleteMatchCustomFormAndTagByTagId(tagId);
    }
}
