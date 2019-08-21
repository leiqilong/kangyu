package com.hlife.server.scenes.handler.updatehandler;

import com.alibaba.fastjson.JSONObject;

/**
 * 检查适配接口
 */
public interface IUpdateAdapter {

    /**
     * 检查统一方法
     * @param jsonObject 检查所需数数
     */
    void update(JSONObject jsonObject);
}
