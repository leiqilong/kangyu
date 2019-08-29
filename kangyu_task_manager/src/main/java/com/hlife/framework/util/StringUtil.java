package com.hlife.framework.util;

/**
 * 自实现字符串工具类
 */
public class StringUtil {
    private StringUtil() {
    }

    public static <T> boolean stringIsNull(T t) {
        return t == null || t.toString().trim().isEmpty();
    }

    public static boolean stringIsNull(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static <T> boolean stringIsNotNull(T t) {
        return !stringIsNull(t);
    }

    public static boolean stringIsNotNull(String str) {
        return !stringIsNull(str);
    }
}
