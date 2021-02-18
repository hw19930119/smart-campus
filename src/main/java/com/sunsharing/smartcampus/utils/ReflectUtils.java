package com.sunsharing.smartcampus.utils;

import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Log4j2
public class ReflectUtils {

    /**
     * 属性名处理
     *
     * @param method
     *            方法(get/set)
     * @param attName
     * @return
     */
    private static String attNameHandle(String method, String attName) {
        StringBuffer res = new StringBuffer(method);
        // 属性只有一个字母
        if (attName.length() == 1) {
            res.append(attName.toUpperCase());
        } else {
            // 属性包含两个字母及以上
            char[] charArray = attName.toCharArray();
        // 当前两个字符为小写时,将首字母转换为大写
            if (Character.isLowerCase(charArray[0]) && Character.isLowerCase(charArray[1])) {
                res.append(Character.toUpperCase(charArray[0]));
                res.append(attName.substring(1));
            } else {
                res.append(attName);
            }
        }

        return res.toString();
    }

    /**
     * 获取属性值
     *
     * @param o
     *            操作对象
     * @param attName
     *            属性名
     * @param returnType
     *            返回值类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Object o, String attName, Class<T> returnType) {
        if (o == null || attName == null || attName.isEmpty()) {
            return null;
        }
        String methodName = attNameHandle("get", attName);

        return (T) Operation(o, methodName, attName, null, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T set(T o, String attName, Object value, Class<?> paramType) {
        if (o == null || attName == null || attName.isEmpty()) {
            return null;
        }
        String methodName = attNameHandle("set", attName);

        return (T) Operation(o, methodName, attName, paramType, value);
    }

    /**
     * @param o
     *            操作对象
     * @param methodName
     *            方法名
     * @param attName
     *            属性名
     * @param value
     *            值
     * @return get方法返回实际值 set方法返回操作后对象
     */
    private static Object Operation(Object o, String methodName, String attName, Class<?> paramType, Object value) {
        // 方法赋值出错标志
        boolean opErr = false;
        Object res = null;
        Class<?> type = o.getClass();
        try {
            Method method = null;
            if (methodName.indexOf("get") != -1) {

                method = type.getMethod(methodName);
                res = method.invoke(o);
            } else {

                paramType = paramType == null ? value.getClass() : paramType;
                method = type.getMethod(methodName, paramType);
                method.invoke(o, value);
                res = o;
            }
        } catch (Exception e) {
            opErr = true;
            log.info("操作失败");
        }

        if (opErr) {
            try {
                Field field = null;
                field = type.getDeclaredField(attName);
                field.setAccessible(true);

                if (methodName.indexOf("get") != -1) {

                    res = field.get(o);

                    field.set(o, value);
                    res = o;
                }
            } catch (Exception e) {
                log.info("操作失败");
            }
        }

        return res;
    }
}
