package com.netease.neteaseioc.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 长按监听
 * author: mayuhai
 * created on: 2019/4/22 9:41 AM
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnLongClickListener", listenerType = View.OnLongClickListener.class, listenerCallback = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
