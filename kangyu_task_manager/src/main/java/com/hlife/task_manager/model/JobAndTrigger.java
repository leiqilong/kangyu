package com.hlife.task_manager.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.Map;

@Data
@Accessors(chain = true)
public class JobAndTrigger {
    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务所在组
     */
    private String jobGroup;

    /**
     * 任务类名
     */
    private String jobClassName;

    /**
     * 任务内容
     */
    private Map<String, Object> jobData;

    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 触发器所在组
     */
    private String triggerGroup;

    /**
     *
     */
    private BigInteger repeatInterval;

    /**
     *
     */
    private BigInteger timesTriggered;

    /**
     * 表达式
     */
    private String cronExpression;

    /**
     * 时区
     */
    private String timeZoneId;
}
