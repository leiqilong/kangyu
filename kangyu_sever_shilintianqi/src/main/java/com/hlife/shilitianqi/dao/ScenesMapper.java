package com.hlife.shilitianqi.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.Scenes;
import org.bson.Document;

import java.util.List;

/**
 * 场景持久层接口
 */
public interface ScenesMapper {

    /**
     * 保存场景基本信息
     *
     * @param scenes 场景基本信息
     * @return 场景基本信息
     */
    Scenes saveScenes(Scenes scenes);

    /**
     * 查询所有场景列表
     *
     * @return 所有场景列表
     */
    List<Scenes> searchScenesListAll();

    /**
     * 分页 查询场景列表
     *
     * @param queryDoc  查询参数
     * @param pageParam 分页参数
     * @return 查询场景列表分页数据
     */
    PageResult<Scenes> searchScenesListByParams(Document queryDoc, PageParam pageParam);

    /**
     * 确定场景数据是否存在
     *
     * @param queryDoc 查询条件
     * @return 是否存在
     */
    boolean isExists(Document queryDoc);

    /**
     * 根据id 删除场景信息
     *
     * @param scenesId 场景 id
     * @return 删除的条数
     */
    Long deleteScenesById(String scenesId);

    /**
     * 修改 场景基本信息
     *
     * @param scenes 场景基本信息
     * @return 场景基本信息
     */
    Scenes updateScenes(Scenes scenes);
}
