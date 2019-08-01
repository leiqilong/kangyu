package com.hlife.framework.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 返回值包装类
 * @param <T> 具体返回类型
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

    private Boolean success;

    private String massage;

    private T resultData;

    public ResultVO(T resultData) {
        this(resultData, "操作成功");
    }

    public ResultVO(T resultData, String successInfo) {
        this(true, successInfo, resultData);
    }

    public ResultVO(Exception e) {
        this(false, e.getMessage(), null);
    }
}
