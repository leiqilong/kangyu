package com.hlife.server.scale.constant;

public class ScaleConstant {

    public static final String MSG_FMT = "{\n"+
            "  \"type\": \"CalculationFormPush\",\n"+
            "  \"data\": {\n"+
            "    \"userList\": [\"%s\"],\n"+
            "    \"title\": \"%s量表测量结果通知\",\n"+
            "    \"doctorName\": \"%s\",\n"+
            "    \"content\": \"%s\",\n"+  // 消息内容，可以是结果截取前几个字......
            "    \"formName\": \"%s\"\n"+ //
            "  }\n"+
            "}";
}
