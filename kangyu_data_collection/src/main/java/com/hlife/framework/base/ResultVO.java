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

    private String message;

    private T resultData;

    public ResultVO(T resultData) {
        this(resultData, "操作成功");
    }

    public ResultVO(T resultData, String message) {
        this(true, message, resultData);
    }

    public ResultVO(Exception e) {
        this(false, e.getMessage(), null);
    }

    public ResultVO(Throwable e) {
        this(false, "未知错误", null);
    }
}
