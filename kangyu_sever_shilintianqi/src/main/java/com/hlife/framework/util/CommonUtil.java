package com.hlife.framework.util;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 公用 工具类
 */
public class CommonUtil {
    private CommonUtil() {
    }

    public static <T> String getCollectionName(Class<T> entityClass) {
        return entityClass.getAnnotation(Document.class).collection();
    }
}
