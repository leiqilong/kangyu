package com.hlife.shilitianqi.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlife.shilitianqi.model.CustomFormTag;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomFormTagMapperTest {

    @Autowired
    private CustomFormTagMapper customFormTagMapper;

    @Test
    public void getCustomFormTagList() {
        JSONObject jsonObject = new JSONObject();
        JSON.toJSONString(jsonObject);
        List<CustomFormTag> customFormTagList = this.customFormTagMapper.getCustomFormTagList(new Document("tagType", "04"));
        log.info("customFormTagList ==> :", customFormTagList);
        assertTrue(customFormTagList.isEmpty());
    }
}
