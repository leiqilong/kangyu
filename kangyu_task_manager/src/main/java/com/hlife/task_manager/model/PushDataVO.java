package com.hlife.task_manager.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
@Accessors(chain = true)
public class PushDataVO implements Serializable {

    private String type;
    private PushDataVO.MyData data;

    @Data
    @Accessors(chain = true)
    public static class MyData {
        private List<RecordVO> userList;
        private List<String> dataGuidList;
        private String title;
        private String content;
    }

    @Getter
    public enum PushType {
        CUSTOM_FORM("formTagPush", "调查问卷", "请点击详情完成调查问卷", PushDataVO::getFormIdList),
        MISSION("pdAndEduTagPush", "宣教", "请点击详情完成宣教", PushDataVO::getPdEduGuidList);
        private String pushType;
        private String title;
        private String content;
        private Function<JSONArray, List<String>> DataGuidList;

        PushType(String pushType, String title, String content, Function<JSONArray, List<String>> DataGuidList) {
            this.pushType = pushType;
            this.title = title;
            this.content = content;
            this.DataGuidList = DataGuidList;
        }

        public static PushType of(String pushType) {
            for (PushType type : PushType.values()) {
                if (type.pushType.equals(pushType)) {
                    return type;
                }
            }

            throw new IllegalArgumentException("没有相应的推送业务");
        }
    }

    private static List<String> getPdEduGuidList(JSONArray jsonArray) {
        List<String> resultList = new ArrayList();
        int i = 0;

        for(int size = jsonArray.size(); i < size; ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            resultList.add(jsonObject.getString("guid"));
        }

        return resultList;
    }

    private static List<String> getFormIdList(JSONArray jsonArray) {
        List<String> resultList = new ArrayList();
        int i = 0;

        for(int size = jsonArray.size(); i < size; ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            resultList.add(jsonObject.getString("leaf"));
        }

        return resultList;
    }
}
