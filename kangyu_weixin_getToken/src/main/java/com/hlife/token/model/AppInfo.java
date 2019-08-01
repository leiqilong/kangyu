package com.hlife.token.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppInfo implements Serializable {

    /**
     * 微信开发者ID
     */
    private String appId;

    /**
     * 微信开发者密码
     */
    private String appSecret;

}
