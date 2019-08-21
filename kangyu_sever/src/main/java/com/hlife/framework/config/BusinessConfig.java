package com.hlife.framework.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BusinessConfig {

    /**
     * 获取用户信息url .net 服务
     */
    @Value("${user-data.url}")
    private String userDataUrl;

    /**
     * 获取用户信息port .net 服务
     */
    @Value("${user-data.port}")
    private String userDataPort;

    /**
     * 消息摧送url .net 服务
     */
    @Value("${msg-publish.url}")
    private String msgPublishUrl;

    /**
     * 消息摧送url .net 服务
     */
    @Value("${msg-publish.port}")
    private String msgPublishPort;
}
