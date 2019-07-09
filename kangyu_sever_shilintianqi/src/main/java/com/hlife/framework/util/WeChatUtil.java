package com.hlife.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信公具类
 */
@Slf4j
public class WeChatUtil {
    private WeChatUtil() {
    }

    /**
     * 推送模版消息
     *
     * @param guid        患者guid
     * @param weChatID    患者微信id
     * @param paramObject 消息 jsonObject
     * @param data        消息具体信息
     */
    public static boolean pushMassage(String guid, String weChatID, String url, JSONObject paramObject, JSONObject data) {
        JSONObject userObject = new JSONObject();
        userObject.put("Weixinid", weChatID);
        userObject.put("guid", guid);

        JSONArray userList = new JSONArray();
        userList.add(userObject);

        data.put("userList", userList);

        paramObject.put("data", data);

        log.info("paramObject ==> {}", paramObject);

        log.info("url ==> {}", url);

        String res = HttpClientUtil.doPost(url, JSON.toJSONString(paramObject));
        log.info("res ==> {}", res);

        JSONObject resObj = JSON.parseObject(res);

        if (resObj == null || !resObj.getBoolean("state")) {
            return false;
        }

        return true;
    }
}
