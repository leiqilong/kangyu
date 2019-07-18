package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.JudgeStandardMapper;
import com.hlife.shilitianqi.handler.checkhandler.ICheckAdapter;
import com.hlife.shilitianqi.model.JudgeStandard;
import com.hlife.shilitianqi.service.JudgeStandardService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场景设备判别服务层实现
 */
@Slf4j
@Service
public class JudgeStandardServiceImpl implements JudgeStandardService, ICheckAdapter {

    @Autowired
    private JudgeStandardMapper judgeStandardMapper;

    @Override
    public JudgeStandard saveOrEditJudgeStandard(JudgeStandard judgeStandard) {
        String judgeStandardId = judgeStandard.getJudgeStandardId();
        if (StringUtil.stringIsNull(judgeStandardId)) {
            judgeStandard.setJudgeStandardId(GuidUtil.generateGuid());
        } else {
            if (!this.judgeStandardMapper.isExists(new Document("judgeStandardId", judgeStandardId))) {
                throw new RuntimeException("谇数据不存在！");
            }
            this.judgeStandardMapper.deleteJudgeStandardByJudgeStandardId(judgeStandardId);
        }
        return this.judgeStandardMapper.saveJudgeStandard(judgeStandard);
    }

    @Override
    public List<JudgeStandard> searchJudgeStandardList(String deviceOfScenesId) {
        return this.judgeStandardMapper.searchJudgeStandardList(deviceOfScenesId);
    }

    @Override
    public Long deleteJudgeStandardByJudgeStandardId(String judgeStandardId) {
        if (!this.judgeStandardMapper.isExists(new Document("judgeStandardId", judgeStandardId))) {
            throw new RuntimeException("谇数据不存在！");
        }
        return this.judgeStandardMapper.deleteJudgeStandardByJudgeStandardId(judgeStandardId);
    }

    @Override
    public Long deleteJudgeStandardByDeviceOfScenesId(String deviceOfScenesId) {
        if (this.judgeStandardMapper.isExists(new Document("deviceOfScenesId", deviceOfScenesId))) {
            return this.judgeStandardMapper.deleteJudgeStandardByDeviceOfScenesId(deviceOfScenesId);
        }
        return 0L;
    }

    @Override
    public boolean check(JSONObject jsonObject) {
        if (jsonObject.isEmpty()) {
            return false;
        }
        if (this.judgeStandardMapper.isExists(new Document("tagId", jsonObject.getString("tagId")))) {
            log.debug("我是设备规则分支！");
            throw new RuntimeException("该标签有对应的场景-设备-规则不能删除！");
        }
        return true;
    }
}
