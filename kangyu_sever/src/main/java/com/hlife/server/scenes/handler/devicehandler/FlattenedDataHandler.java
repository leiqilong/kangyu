package com.hlife.server.scenes.handler.devicehandler;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.JSONUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.scenes.model.Device;
import com.hlife.server.scenes.model.DeviceResult;

import java.util.Arrays;
import java.util.Comparator;

import static com.hlife.framework.util.StringUtil.REG_POINT;
import static com.hlife.framework.util.StringUtil.VERTICAL_LINE;

public class FlattenedDataHandler {

    /**
     * 通用平行化数据算法
     *
     * @param jsonObject 原数据
     * @param paths      数值数径
     * @return 平行化后的数据
     */
    public static DeviceResult.Record getParameterCommon(JSONObject jsonObject, Device.FieldPath[] paths) {
        if (paths == null || paths.length == 0) {
            throw new IllegalArgumentException("取值规则不能为空");
        }

        if (paths.length == 1) {
            return new DeviceResult.Record().setTwiceValue(JSONUtil.getValue(jsonObject, paths[0].getPath().split(REG_POINT)));
        }

        Arrays.sort(paths, Comparator.comparing(Device.FieldPath::getVariable));
        String[] value = Arrays.stream(paths)
                .map(path -> JSONUtil.getValue(jsonObject, path.getPath().split(REG_POINT)))
                .toArray(String[]::new);

        return new DeviceResult.Record().setTwiceValue(StringUtil.join(value, VERTICAL_LINE));
    }

}
