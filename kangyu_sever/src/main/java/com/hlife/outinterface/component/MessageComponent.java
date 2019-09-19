package com.hlife.outinterface.component;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.config.BusinessConfig;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.framework.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageComponent {

    @Autowired
    private BusinessConfig businessConfig;

    /**
     * 推送模版消息
     *
     * @param guid        患者guid
     * @param weChatID    患者微信id
     * @param paramObject 消息 jsonObject
     * @param data        消息具体信息
     */
    public void pushWeChatMassage(String guid, String weChatID, JSONObject paramObject, JSONObject data) {
        String url = String.format(
                HttpClientUtil.HTTP_URL_FORMAT,
                businessConfig.getMsgPublishUrl(),
                businessConfig.getMsgPublishPort(),
                "wpa/msg/sendPubMsg"
        );

        if (!WeChatUtil.pushMassage(guid, weChatID, url, paramObject, data)) {
            throw new RuntimeException("推送消息失败");
        }
    }
}
