package com.hlife.outinterface.component;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.config.BusinessConfig;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.outinterface.entity.EventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiComponent {

    private String upDevicData = "api/Public/DevicData/Post";

    @Autowired
    private BusinessConfig businessConfig;

    public String devicDataPost(EventData eventData) {
        return HttpClientUtil.doPost(
                String.format(
                        HttpClientUtil.HTTP_URL_FORMAT,
                        businessConfig.getUserDataUrl(),
                        businessConfig.getUserDataPort(),
                        upDevicData
                ), JSONObject.toJSONString(eventData)
        );
    }
}
