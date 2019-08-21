package com.hlife.server.scale.dao;

import com.hlife.server.scale.model.MChat;
import org.bson.Document;

import java.util.List;

/**
 * MChat 持久层接口
 */
public interface MChatMapper {

    /**
     * 保存MChat记录
     *
     * @param mChat MChat记录
     * @return 保存的MChat记录
     */
    MChat saveMChat(MChat mChat);

    /**
     * 查询一条数据
     *
     * @param queryDoc 查询条件 <br/>
     *                 chatId 主键
     * @return MChat记录
     */
    MChat findOne(Document queryDoc);

    /**
     * 删除
     *
     * @param queryDoc 查询条件 <br/>
     *                 chatId 主键
     * @return 删除的条数
     */
    Long delete(Document queryDoc);

    /**
     * 查询历史记录
     *
     * @param queryDoc 查询条件 <br/>
     *                 guid 患者guid
     * @return MChat 历史记录
     */
    List<MChat> findMChatHistory(Document queryDoc);
}
