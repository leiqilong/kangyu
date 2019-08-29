package com.hlife.task_manager.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class JobAndTrigger {

    /**
     * 任务名称
     */
    private String JOB_NAME;

    /**
     * 任务所在组
     */
    private String JOB_GROUP;

    /**
     * 任务类名
     */
    private String JOB_CLASS_NAME;

    /**
     * 触发器名称
     */
    private String TRIGGER_NAME;

    /**
     * 触发器所在组
     */
    private String TRIGGER_GROUP;

    /**
     *
     */
    private BigInteger REPEAT_INTERVAL;

    /**
     *
     */
    private BigInteger TIMES_TRIGGERED;

    /**
     * 表达式
     */
    private String CRON_EXPRESSION;

    /**
     * 时区
     */
    private String TIME_ZONE_ID;
}
