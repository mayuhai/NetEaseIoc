package com.netease.neteaseioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 事件注解上的注解
 * author: mayuhai
 * created on: 2019/4/22 9:46 AM
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    //设置监听方法
    String listenerSetter();

    //监听类
    Class<?> listenerType();

    //监听类的回调方法
    String listenerCallback();

}
