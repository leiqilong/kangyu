package com.hlife.server.scenes.dao;

import com.hlife.server.scenes.model.JudgeStandard;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * 场景设备判别持久层接口
 */
public interface JudgeStandardMapper {

    /**
     * 新增 场景设备判别信息
     *
     * @param judgeStandard 场景设备判别信息
     * @return 场景设备判别信息
     */
    JudgeStandard saveJudgeStandard(JudgeStandard judgeStandard);

    /**
     * 根据场景设备id 查询判别规则列表
     *
     * @param deviceOfScenesId 场景设备id
     * @return 判别规则列表
     */
    List<JudgeStandard> searchJudgeStandardList(String deviceOfScenesId);

    /**
     * 根据主键 删除 查询判别规则数据
     *
     * @param judgeStandardId 判别 id
     * @return 影响的行数
     */
    Long deleteJudgeStandardByJudgeStandardId(String judgeStandardId);

    /**
     * 判断 判别规则数据是否存在
     *
     * @param queryDoc 判别 条伯
     * @return 是否存在
     */
    boolean isExists(Document queryDoc);

    /**
     * 根据设备id 删除 对应的判别规则
     *
     * @param deviceOfScenesId 设备id
     * @return 删除的条数
     */
    Long deleteJudgeStandardByDeviceOfScenesId(String deviceOfScenesId);

    void update(Document queryDoc, Update update);
}
