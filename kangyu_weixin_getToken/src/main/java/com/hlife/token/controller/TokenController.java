package com.hlife.task_manager.controller;

import com.hlife.task_manager.model.AppInfo;
import com.hlife.task_manager.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("getToken")
    public String getToken(@RequestBody AppInfo appInfo) {
        return tokenService.getToken(appInfo);
    }
}
