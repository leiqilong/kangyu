package com.hlife.shilitianqi.service;

import com.hlife.shilitianqi.model.JudgeStandard;

import java.util.List;

/**
 * 场景设备判别服务层接口
 */
public interface JudgeStandardService {

    /**
     * 新增/修改场景设备判别数据
     *
     * @param judgeStandard 场景设备判别数据
     * @return 场景设备判别数据
     */
    JudgeStandard saveOrEditJudgeStandard(JudgeStandard judgeStandard);

    /**
     * 根据场景设备id 查询 场景设备判别数据
     *
     * @param deviceOfScenesId 场景设备id
     * @return  场景设备判别数据list
     */
    List<JudgeStandard> searchJudgeStandardList(String deviceOfScenesId);

    /**
     * 根据主键删除 场景设备判别数据
     *
     * @param judgeStandardId 场景设备判别数据 id
     * @return 影响的行数
     */
    Long deleteJudgeStandardByJudgeStandardId(String judgeStandardId);

    /**
     * 根据设备id 删除 规则 数据
     *
     * @param deviceOfScenesId 设备id
     * @return 影响的行数
     */
    Long deleteJudgeStandardByDeviceOfScenesId(String deviceOfScenesId);
}
