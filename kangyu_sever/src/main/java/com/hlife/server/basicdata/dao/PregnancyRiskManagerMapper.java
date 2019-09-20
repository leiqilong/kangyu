package com.hlife.server.basicdata.dao;

import com.hlife.server.basicdata.model.PregnancyRiskManager;
import org.bson.Document;

import java.util.List;

/**
 * 孕产妇妊娠风险管理持久层接口
 */
public interface PregnancyRiskManagerMapper {

    /**
     * 保存妊娠风险信息
     *
     * @param pregnancyRiskManager 妊娠风险信息
     * @return 保存的妊娠风险信息
     */
    PregnancyRiskManager savePregnancyRiskManager(PregnancyRiskManager pregnancyRiskManager);

    /**
     * 根据主键查询风险信息
     *
     * @param prmId 主键
     * @return 风险信息
     */
    PregnancyRiskManager findPregnancyRiskManagerByPrmId(String prmId);

    /**
     * 数据存在
     *
     * @param prmId 主键
     * @return 数据存在
     */
    boolean isExits(String prmId);

    /**
     * 删除妊娠风险信息
     *
     * @param prmId 主键
     * @return 删除的行数
     */
    long deletePregnancyRiskManager(String prmId);

    /**
     * 是否存在子节点
     *
     * @param prmId 主键
     * @return true 存在子节点
     */
    boolean isExitsChildren(String prmId);

    /**
     * 修改 风险信息
     *
     * @param pregnancyRiskManager 风险信息
     * @return 修改的条数
     */
    long editPregnancyRiskManager(PregnancyRiskManager pregnancyRiskManager);

    /**
     * 查询妊娠风险信息列表
     *
     * @param document 参数
     * @return 妊娠风险信息列表
     */
    List<PregnancyRiskManager> searchPregnancyRisk(Document document);
}
