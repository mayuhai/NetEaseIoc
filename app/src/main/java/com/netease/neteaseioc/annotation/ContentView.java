package com.netease.neteaseioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * setContentView(R.layout.activity_main)
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) //jvm 在运行时 通过反射的机制获取注解的值
public @interface ContentView {
    int value();
}
