package com.hlife.framework.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import static com.hlife.framework.util.StringUtil.BLANK_SPACE;
import static com.hlife.framework.util.StringUtil.EMPTY_STR;


@Slf4j
public class JSONUtil {

    private JSONUtil() {
    }

    public static String getValue(JSONObject jsonObject, String... paths) {
        return getValue(jsonObject, 0, paths);
    }

    public static String getValue(JSONObject jsonObject, int i, String... paths) {
        String path = paths[i];

        log.debug("index ==> {}, path ==> {}, jsonObject ==> {}", i, path, jsonObject);
        int lengh = paths.length;
        i++;
        if (lengh == i && path.indexOf("[0]") == -1) {
            String result = jsonObject.getString(path);
            if (StringUtil.stringIsNull(result)) {
                return result;
            }
            return jsonObject.getString(path).replaceAll("[＞﹥]", ">")
                    .replaceAll("[＜﹤]", "<")
                    .replaceAll("[≤≦]", "<=")
                    .replaceAll("[≧≥]", ">=")
                    .replaceAll(BLANK_SPACE, EMPTY_STR).trim();

        }
        if (path.indexOf("[0]") > -1) {
            JSONArray jsonArray = jsonObject.getJSONArray(path.replace("[0]", ""));
            return getValue(jsonArray, i, paths);
        }

        JSONObject jsonObject1 = jsonObject.getJSONObject(path);
        return getValue(jsonObject1, i, paths);
    }

    public static String getValue(JSONArray jsonArray, int i, String... paths) {
        log.debug("index ==> {}, path ==> {},  jsonArray ==> {}", i, paths, jsonArray);

        int last = jsonArray.size() - 1;
        if (jsonArray.get(last) instanceof JSONArray) {
            JSONArray jsonArray1 = jsonArray.getJSONArray(last);
            return getValue(jsonArray1, i, paths);
        }
        if (jsonArray.get(last) instanceof JSONObject) {
            JSONObject jsonObject = jsonArray.getJSONObject(last);
            return getValue(jsonObject, i, paths);
        }

        return jsonArray.getString(last);
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject.put("a", jsonObject1);
        JSONArray jsonArray = new JSONArray();
        jsonObject1.put("b", jsonArray);
        JSONObject jsonObject2 = new JSONObject();
        jsonArray.add(jsonObject2);
        jsonObject2.put("c", "lql");
        JSONArray jsonArray2 = new JSONArray();
        jsonObject2.put("d", jsonArray2);
        jsonArray2.add("jwt");
        JSONObject jsonObject3 = new JSONObject();
        jsonObject2.put("e", jsonObject3);
        jsonObject3.put("f", "hao");
        JSONArray jsonArray3 = new JSONArray();
        jsonObject2.put("g", jsonArray3);
        JSONArray jsonArray4 = new JSONArray();
        jsonArray3.add(jsonArray4);
        jsonArray4.add("1314");

        String path1 = "a_b[0]_c";
        String path2 = "a_b[0]_d[0]";
        String path3 = "a_b[0]_e_f";
        String path4 = "a_b[0]_g[0][0]";

        log.debug("jsonObject==>{}", jsonObject);
        log.debug("p1==>{}", getValue(jsonObject, path1.split("_")));
        log.debug("p2==>{}", getValue(jsonObject, path2.split("_")));
        log.debug("p3==>{}", getValue(jsonObject, path3.split("_")));
        log.debug("p4==>{}", getValue(jsonObject, path4.split("_")));
    }
}
