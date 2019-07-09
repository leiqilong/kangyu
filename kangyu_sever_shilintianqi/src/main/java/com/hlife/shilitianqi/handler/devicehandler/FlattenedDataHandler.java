package com.hlife.shilitianqi.handler.devicehandler;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class FlattenedDataHandler {

    public static List<String> getParameterCommon(JSONObject jsonObject, String[] paths) {
        List<String> resultList = new ArrayList<>();

        for (String path: paths) {
            resultList.add(JSONUtil.getValue(jsonObject, 0, path.split("_")));
        }

        return resultList;
    }
}
