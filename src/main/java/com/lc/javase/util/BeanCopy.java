package com.lc.javase.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class BeanCopy extends BeanUtils {
    public static <T> T copyBeanByJson(Object sourceObject, Class<T> targetClazz) {
        return stringToObject(JSON.toJSONString(sourceObject), targetClazz);
    }

    public static <E> E copyBeanByReflection(Object sourceObject, Class<E> targetClazz) throws Exception {
        E appBean = targetClazz.newInstance();
        appBean = replaceProperties(sourceObject, appBean);
        return appBean;
    }

    public static <E> List<E> copyListByReflection(Object sourceObject, Class<E> targetClazz) throws Exception {
        if (null == sourceObject || !(sourceObject instanceof ArrayList<?>)) {
            return null;
        }
        List<E> result = new ArrayList<E>();
        List<?> sourceList = null;
        if (sourceObject instanceof ArrayList<?>) {
            sourceList = (ArrayList<?>) sourceObject;
        }
        for (int i = 0; null != sourceList && i < sourceList.size(); i++) {
            E appBean = targetClazz.newInstance();
            appBean = replaceProperties(sourceList.get(i), appBean);
            result.add(appBean);
        }
        return result;
    }

    private static <T> T stringToObject(String source, Class<T> targetClazz) {
        try {
            return JSONObject.parseObject(source, targetClazz);
        } catch (Exception e) {
            System.out.println("stringToObject:字符串转JSONObject异常!");
        }
        return null;
    }

    private static <E> E replaceProperties(Object source, E target) throws Exception {
        if (null == source || null == target) {
            return null;
        }
        Class actualEditable = target.getClass();
        // 获取copy的属性
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (int i = 0; i < targetPds.length; i++) {
            PropertyDescriptor targetPd = targetPds[i];
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());// 从类中获取属性
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        Class sourceClass = sourcePd.getPropertyType();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source, new Object[0]);
                        if (null != value && !(sourceClass.equals(int.class) && (int) value == 0)) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, new Object[]{value});
                        }
                    } catch (Exception ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
        return target;
    }
}
