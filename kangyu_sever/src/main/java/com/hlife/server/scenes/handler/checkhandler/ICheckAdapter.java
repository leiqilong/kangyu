package com.hlife.server.scenes.handler.checkhandler;

import com.alibaba.fastjson.JSONObject;

/**
 * 检测适配器
 */
public interface ICheckAdapter {

    boolean check(JSONObject jsonObject);
}
