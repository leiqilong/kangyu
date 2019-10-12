package com.hlife.server.scenes.handler.devicehandler;

import com.alibaba.fastjson.JSONArray;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.scenes.model.DeviceResult;
import com.hlife.server.scenes.model.JudgeStandard;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.hlife.framework.util.StringUtil.*;

@Slf4j
public class DeviceResultHandler {
    private static ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");

    private final static String[] vars = {"X", "Y", "Z"};

    /**
     * 通用标签 计算算法
     *
     * @param judgeStandardList 规则
     * @param values            表达式
     * @return 设备计算结果
     */
    public static DeviceResult getDeviceResultCommon(List<JudgeStandard> judgeStandardList, String values) {
        log.debug("values ==> {}", values);

        checkStandard(judgeStandardList);

        if (stringIsNull(values)) {
            return getDeviceResult(judgeStandardList);
        }

        for (JudgeStandard judgeStandard : judgeStandardList) {
            String ruler = judgeStandard.getRuler();
            if (StringUtil.stringIsNull(ruler)) {
                continue;
            }

            boolean match = false;
            try {
                Object obj = scriptEngine.eval(getExpression(values, StringUtil.formatExpression(ruler)));
                if (obj instanceof Boolean) {
                    match = (boolean) obj;
                } else {
                    match = false;
                }
                //match = (boolean) scriptEngine.eval(getExpression(values, judgeStandard.getRuler()));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            if (match) {
                return new DeviceResult()
                        .setTagName(judgeStandard.getTagName())
                        .setTagValue(judgeStandard.getTagValue())
                        .setTagId(judgeStandard.getTagId())
                        .setScore(judgeStandard.getScore());
            }
        }

        return getDeviceResult(judgeStandardList);
    }

    /**
     * 量表/自定义表单计算结果
     * <p>
     * 数据为字符串,采用 equals 对比
     */
    public static DeviceResult getDeviceResultForm(List<JudgeStandard> judgeStandardList, String param) {
        log.debug("param ==> {}", param);

        checkStandard(judgeStandardList);

        if (stringIsNull(param)) {
            return getDeviceResult(judgeStandardList);
        }

        param = param.trim();

        for (JudgeStandard judgeStandard : judgeStandardList) {
            String ruler = judgeStandard.getRuler();

            if (stringIsNull(ruler)) {
                continue;
            }

            boolean match = StringUtil.formatExpression(ruler).equals(param);

            log.debug("ruler:{}, param:{}, match:{}", ruler, param, match);

            if (match) {
                return new DeviceResult()
                        .setTagName(judgeStandard.getTagName())
                        .setTagValue(judgeStandard.getTagValue())
                        .setTagId(judgeStandard.getTagId())
                        .setScore(judgeStandard.getScore());
            }
        }

        return getDeviceResult(judgeStandardList);
    }

    /**
     * 情绪类匹配方法
     * <p>
     * 将规则字符串分成 list
     * list 包涵当前值 则true
     *
     * @param judgeStandardList 规则列表
     * @param param             当前值
     * @return 返回结果
     */
    public static DeviceResult getDeviceResultMood(List<JudgeStandard> judgeStandardList, String param) {
        log.debug("param ==> {}", param);

        checkStandard(judgeStandardList);

        if (stringIsNull(param)) {
            return getDeviceResult(judgeStandardList);
        }

        param = param.trim();

        for (JudgeStandard judgeStandard : judgeStandardList) {
            String ruler = judgeStandard.getRuler();

            if (stringIsNull(ruler)) {
                continue;
            }

            List<String> modeList = Arrays.asList(ruler.trim().split(BLANK_SPACE));

            boolean match = modeList.contains(param);

            log.debug("ruler:{}, param:{}, match:{}", ruler, param, match);

            if (match) {
                return new DeviceResult()
                        .setTagName(judgeStandard.getTagName())
                        .setTagValue(judgeStandard.getTagValue())
                        .setTagId(judgeStandard.getTagId())
                        .setScore(judgeStandard.getScore());
            }
        }

        return getDeviceResult(judgeStandardList);
    }

    public static DeviceResult getDeviceResultPendi(List<JudgeStandard> judgeStandardList, String param) {
        log.info("paramPendi ==> {}", param);

        checkStandard(judgeStandardList);

        JSONArray jsonArray = JSONArray.parseArray(param.trim());

        JudgeStandard judgeStandard = judgeStandardList.stream()
                .filter(Objects::nonNull)
                /*.sorted(Comparator.comparing(JudgeStandard::getScore).reversed())*/
                .filter(standard -> apply(standard, jsonArray))
                .max(Comparator.comparing(JudgeStandard::getScore))
                .orElse(judgeStandardList.get(0));


        return Objects.nonNull(judgeStandard)
                ? new DeviceResult()
                .setTagName(judgeStandard.getTagName())
                .setTagValue(judgeStandard.getTagValue())
                .setTagId(judgeStandard.getTagId())
                .setScore(judgeStandard.getScore())
                : getDeviceResult(judgeStandardList);
    }

    private static boolean apply(JudgeStandard standard, JSONArray jsonArray) {
        String ruler = standard.getRuler();

        if (stringIsNull(ruler)) {
            return false;
        }

        List<String> symptomList = Arrays.asList(ruler.trim().split(BLANK_SPACE));
        for (String symptom : symptomList) {
            if (jsonArray.contains(symptom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接表达式
     *
     * @param valuesStr 参数表达式
     * @param ruler     规则表芝式
     * @return 判断表达式
     */
    private static String getExpression(String valuesStr, String ruler) {
        String[] values = valuesStr.replaceAll(SLASH, VERTICAL_LINE).split(REG_VERTICAL_LINE);
        StringBuilder expr = new StringBuilder();
        for (int i = 0, length = values.length; i < length; i++) {
            expr.append(getSingleExpression(vars[i], values[i])).append(SEMICOLON);
        }
        String expression = String.format("%s%s", expr.toString(), ruler.replaceAll(PERCENT, EMPTY_STR)).toUpperCase();
        log.debug("expression ==> {}", expression);
        return expression;
    }

    /**
     * 单项表达式
     *
     * @param variable 变量 x, y
     * @param value    值
     * @return 单项表达式
     */
    private static String getSingleExpression(String variable, String value) {
        return String.format("%s=%s", variable, value);
    }

    /**
     * 检查standard 不为空
     *
     * @param judgeStandardList 规则list
     */
    private static void checkStandard(List<JudgeStandard> judgeStandardList) {
        if (judgeStandardList == null || judgeStandardList.isEmpty()) {
            throw new RuntimeException("请正确维护场景-设备-规则");
        }
    }


    /**
     * 默认值
     *
     * @param judgeStandardList 规则列表
     * @return 默认值
     */
    private static DeviceResult getDeviceResult(List<JudgeStandard> judgeStandardList) {
       /* JudgeStandard defaultJudgeStandard = judgeStandardList.stream()
                .filter(judgeStandard -> judgeStandard.getScore() == 0d)
                .findFirst().orElse(judgeStandardList.get(0));*/
        return new DeviceResult()
                /*.setTagId(defaultJudgeStandard.getTagId())
                .setTagName(defaultJudgeStandard.getTagName())
                .setTagValue(defaultJudgeStandard.getTagValue())*/
                .setScore(0d);
    }
}
