package com.hlife.server.rpc.service;

import com.alibaba.fastjson.JSONArray;

public interface RpcService {

    JSONArray GetTjspByLabels(JSONArray jsonArray);

    String GetJhfspByLabels();
}
