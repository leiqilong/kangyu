package com.hlife.task_manager.service;

import com.hlife.task_manager.model.AppInfo;

public interface TokenService {

    String getToken(AppInfo appInfo);
}
