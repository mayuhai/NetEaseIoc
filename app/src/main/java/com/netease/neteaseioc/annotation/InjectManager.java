package com.netease.neteaseioc.annotation;

import android.app.Activity;
import android.view.View;

import com.netease.neteaseioc.annotation.proxy.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 注入Manager
 */
public class InjectManager {

    public static void inject(Activity activity) {
        //注入布局
        injectLayout(activity);

        //注入View
        injectView(activity);

        //注入事件
        injectEvent(activity);
    }

    /**
     * /注入布局
     * @param activity
     */
    private static void injectLayout(Activity activity) {

        //获取class
        Class<? extends Activity> clazz = activity.getClass();

        //获取该class 上的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);

        //获取注解里的值 R.layout.activity_main
        if (contentView != null) {
            int layoutId = contentView.value();

            //设置setContentView方法
            //方法一
//            activity.setContentView(layoutId);

            //方法二 通过反射方法
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                if (method != null) {
                    method.invoke(activity, layoutId);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * /注入View
     * @param activity
     */
    private static void injectView(Activity activity) {
        //获取class
        Class<? extends Activity> clazz = activity.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //获取field上的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if(injectView != null) {
                int viewId = injectView.value();
                try {
                    //获取findViewById 方法类
                    Method findViewByIdMethod = clazz.getMethod("findViewById", int.class);

                    //执行获取findViewById 并获取返回值
                    Object view = findViewByIdMethod.invoke(activity, viewId);

                    //设置私有属性可以访问、设置
                    field.setAccessible(true);

                    //更新对应注解下的属性值
                    field.set(activity, view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * /注入事件
     * @param activity
     */
    private static void injectEvent(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();

        //获取本类所有方法
        Method[] methods = clazz.getDeclaredMethods();

        //遍历方法
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();

            if (annotations != null && annotations.length > 0) {
                for (Annotation annotation : annotations) {
                    //获取注解上的注解类型
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    if(annotationType != null) {

                        //获取注解上的注解EventBase
                        EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                        if (eventBase != null) {
                            String listenerSetter = eventBase.listenerSetter();
                            Class<?> listenerType = eventBase.listenerType();
                            String listenerCallback = eventBase.listenerCallback();

                            //获取注解的值
                            try {
                                // 通过annotationType 获取OnClick注解的value值
                                Method valueMethod = annotationType.getDeclaredMethod("value");

                                //执行方法 value() (因为value方法的返回值就是view的id int[] value();) 获取对ids
                                int[] viewIds = (int[]) valueMethod.invoke(annotation);

                                //获取拦截的方法 onClick() onLongClick()
//                                Method listenerTypeMethod = listenerType.getClass().getMethod(listenerCallback);

                                //创建aop拦截类
                                ListenerInvocationHandler handler = new ListenerInvocationHandler();
                                handler.setTarget(activity);
                                handler.addInvocationMethod(listenerCallback, method);

                                //代理
                                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[] {listenerType}, handler);

                                for (int viewId : viewIds) {
                                    //获取findViewById 方法类
                                    View view = activity.findViewById(viewId);

                                    //获取 view 里的 setOnClickListener(new View.OnClickListener())类似这样的方法
                                    Method setter = view.getClass().getMethod(listenerSetter, listenerType);

                                    //执行 view 里的 setOnClickListener(new View.OnClickListener())类似这样的方法
                                    //参数应该是具体的实体而不应该是class
                                    setter.invoke(view, listener);
                                }
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        }
    }
}
