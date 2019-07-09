package com.hlife.framework.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONUtil {

    private JSONUtil() {
    }

    public static String getValue(JSONObject jsonObject, int i, String... paths) {
        String path = paths[i];

        log.info("index ==> {}, path ==> {}, jsonObject ==> {}", i, path, jsonObject);
        int lengh = paths.length;
        i++;
        if (lengh == i) {
            return jsonObject.getString(path);
        }
        if (path.indexOf("[0]") > -1) {
            JSONArray jsonArray = jsonObject.getJSONArray(path.replace("[0]", ""));
            return getValue(jsonArray, i, paths);
        } else {
            JSONObject jsonObject1 = jsonObject.getJSONObject(path);
            return getValue(jsonObject1, i, paths);
        }
    }

    public static String getValue(JSONArray jsonArray, int i, String... paths) {

        String path = paths[i];

        log.info("index ==> {}, path ==> {},  jsonArray ==> {}", i, path, jsonArray);

        i++;
        int lengh = paths.length;
        if (lengh == i) {
            return jsonArray.getString(0);
        }
        if (path.indexOf("[0]") > -1) {
            JSONArray jsonArray1 = jsonArray.getJSONArray(0);
            return getValue(jsonArray1, i, paths);
        } else {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            return getValue(jsonObject, i, paths);
        }
    }
}
