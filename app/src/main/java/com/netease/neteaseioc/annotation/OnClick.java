package com.netease.neteaseioc.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * author: mayuhai
 * created on: 2019/4/22 9:41 AM
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnClickListener", listenerType = View.OnClickListener.class, listenerCallback = "onClick")
public @interface OnClick {
    int[] value();
}
