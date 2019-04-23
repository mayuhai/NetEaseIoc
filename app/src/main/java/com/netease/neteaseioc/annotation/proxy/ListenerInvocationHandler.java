package com.netease.neteaseioc.annotation.proxy;

import android.text.TextUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Description:
 * author: mayuhai
 * created on: 2019/4/22 12:59 PM
 */
public class ListenerInvocationHandler implements InvocationHandler {

    //代理对象
    private Object target;

    //拦截的方法map
    private HashMap<String, Method> invocationMethodsMap = new HashMap<>();

    public void setTarget(Object target) {
        this.target = target;
    }

    public void addInvocationMethod(String methodName, Method method) {
        invocationMethodsMap.put(methodName, method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (target == null) {
            return null;
        }
        String methodName = method.getName();
        if (TextUtils.isEmpty(methodName)) {
            return null;
        }

        //把要拦截的方法赋值给原来的方法
        //method.setAccessible(true); 不起作用，因为后面method又重新赋值,需要放在后面才可以
        method = invocationMethodsMap.get(methodName);
        method.setAccessible(true);

        if (method == null) {
            return null;
        }
        //新方法执行
        if (method.getParameterTypes() != null && method.getParameterTypes().length > 0) {
            if (method.getReturnType() != null) {//是否有返回值 ，如果有返回值，就要主动返回默认值
                method.invoke(target, args);
                return false;
            }
            return method.invoke(target, args);
        } else {
            if (method.getReturnType() != null) {
                method.invoke(target);
                return false;
            }
            return method.invoke(target);
        }
    }
}
