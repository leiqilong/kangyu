package com.hlife.server.basicdata.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.framework.util.TreeUtil;
import com.hlife.server.basicdata.dao.PregnancyRiskManagerMapper;
import com.hlife.server.basicdata.model.PregnancyRiskManager;
import com.hlife.server.basicdata.service.PregnancyRiskManagerService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 孕产妇妊娠风险管理业务层实现
 */
@Service
public class PregnancyRiskManagerServiceImpl implements PregnancyRiskManagerService {

    @Autowired
    private PregnancyRiskManagerMapper pregnancyRiskManagerMapper;

    @Override
    public PregnancyRiskManager saveOrEditPregnancyRiskManager(PregnancyRiskManager pregnancyRiskManager) {
        String prmId = Objects.requireNonNull(pregnancyRiskManager, "传入参数为空").getPrmId();

        if (StringUtil.stringIsNull(prmId)) {
            prmId = GuidUtil.generateGuid();
            pregnancyRiskManager.setPrmId(prmId)
                    .setCreateTime(new Date())
                    .setNodeId(prmId);

            return this.pregnancyRiskManagerMapper.savePregnancyRiskManager(pregnancyRiskManager);
        }

        PregnancyRiskManager record = Objects.requireNonNull(this.pregnancyRiskManagerMapper.findPregnancyRiskManagerByPrmId(prmId), "该数据已不存在");
        pregnancyRiskManager.setCreateTime(record.getCreateTime());

        this.pregnancyRiskManagerMapper.editPregnancyRiskManager(pregnancyRiskManager);

        return pregnancyRiskManager;
    }

    @Override
    public long deletePregnancyRiskManager(String prmId) {
        if (this.nonExits(Objects.requireNonNull(prmId, "传入参数为空"))) {
            throw new RuntimeException("数据不存在");
        }

        if (this.isExitsChildren(prmId)) {
            throw new RuntimeException("存在子节点");
        }

        return this.pregnancyRiskManagerMapper.deletePregnancyRiskManager(prmId);
    }

    @Override
    public List<PregnancyRiskManager> searchPregnancyRisk(JSONObject jsonObject) {
        return this.pregnancyRiskManagerMapper.searchPregnancyRisk(new Document());
    }

    @Override
    public List<PregnancyRiskManager> searchPregnancyRiskTree(JSONObject jsonObject) {
        return TreeUtil.createTree(this.searchPregnancyRisk(jsonObject));
    }

    /**
     * 数据不存在
     *
     * @param prmId 主键
     * @return true 不存在
     */
    private boolean nonExits(String prmId) {
        return !this.isExits(prmId);
    }

    /**
     * 数据存在
     *
     * @param prmId 主键
     * @return true 存在
     */
    private boolean isExits(String prmId) {
        return this.pregnancyRiskManagerMapper.isExits(prmId);
    }

    /**
     * 存在子节点
     *
     * @param prmId
     * @return
     */
    private boolean isExitsChildren(String prmId) {
        return this.pregnancyRiskManagerMapper.isExitsChildren(prmId);
    }
}
