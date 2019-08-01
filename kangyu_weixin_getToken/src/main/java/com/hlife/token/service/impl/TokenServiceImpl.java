package com.hlife.token.service.impl;

import com.hlife.framework.util.HttpClientUtil;
import com.hlife.token.model.AppInfo;
import com.hlife.token.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    @Override
    public String getToken(AppInfo appInfo) {
        return HttpClientUtil.doGet(String.format(tokenUrl, appInfo.getAppId(), appInfo.getAppSecret()));
    }
}
