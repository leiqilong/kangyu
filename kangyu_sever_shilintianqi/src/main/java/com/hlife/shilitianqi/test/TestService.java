package com.hlife.shilitianqi.test;

import com.alibaba.fastjson.JSONObject;
import com.hlife.shilitianqi.handler.checkhandler.ICheckAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestService implements ICheckAdapter {
    @Override
    public boolean check(JSONObject jsonObject) {
        log.debug("我是个测试分支");
        return false;
    }
}
