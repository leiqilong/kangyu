package com.hlife.framework.util;

import org.springframework.context.ApplicationContext;

public class SpringContextUtil {

    private SpringContextUtil() {
    }

    private static ApplicationContext applicationContext;

    //获取上下文
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //设置上下文
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    //通过名字获取上下文中的bean
    public static <T>T getBean(String name, Class<T> tClass){
        return applicationContext.getBean(name, tClass);
    }

    //通过类型获取上下文中的bean
    public static <T>T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }
}
