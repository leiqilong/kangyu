package com.hlife.shilitianqi.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("1", jsonArray);
        JSONObject jsonObject1 = new JSONObject();
        jsonArray.add(jsonObject1);
        JSONArray jsonArray1 = new JSONArray();
        jsonObject1.put("2", jsonArray1);
        JSONArray jsonArray2 = new JSONArray();
        jsonArray1.add(jsonArray2);
        jsonArray2.add("lql");

        log.info("jsonObject ==> {}", jsonObject);
        log.info("value ==> {}", getValue(jsonObject, 0, "1[0]", "0", "2[0]", "[0]", "0"));


    }

    public static String getValue(JSONObject jsonObject, int i, String... paths) {
        String path = paths[i];
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
        int lengh = paths.length;
        i++;
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
