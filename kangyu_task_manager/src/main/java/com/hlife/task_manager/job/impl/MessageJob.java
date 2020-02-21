package com.hlife.task_manager.job.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.task_manager.job.BaseJob;
import com.hlife.task_manager.model.JobMapR;
import com.hlife.task_manager.model.PushDataVO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MessageJob implements BaseJob {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetailImpl jobDetail = (JobDetailImpl) context.getJobDetail();
        JobMapR jobMapR = JSONObject.parseObject(JSON.toJSONString(jobDetail.getJobDataMap()), JobMapR.class);
        PushDataVO.PushType pushType = PushDataVO.PushType.of(jobMapR.getPushType());
        PushDataVO.MyData data = new PushDataVO.MyData()
                .setTitle(pushType.getTitle())
                .setContent(pushType.getContent())
                .setUserList(
                        jobMapR.getRecords().stream()
                                .map(recordVO -> recordVO.setWeixinid(recordVO.getWid()))
                                .collect(Collectors.toList())
                )
                .setDataGuidList(
                        pushType.getDataGuidList().apply(jobMapR.getForms())
                );
        PushDataVO pushDataVO = new PushDataVO()
                .setType(jobMapR.getPushType())
                .setData(data);
        log.info("pushDataVO ==> {}", JSONObject.toJSONString(pushDataVO));
        log.info("port ==> {}", HttpClientUtil.MSG_CONFIG_URL);
        String result = HttpClientUtil.doPost(
                String.format("http://%s%s",
                        HttpClientUtil.MSG_CONFIG_URL,
                        "wpa/msg/sendPubMsg"
                ),
                JSONObject.toJSONString(pushDataVO)
        );
        log.info("result ==> {}", result);
    }
}
