package com.example.demo.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * 常用工具类
 * <p>
 * final 防继承
 * 
 * @author XiongJian
 *
 */
public final class CommUtil {

    /**
     * 防实例化
     */
    private CommUtil() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
        }
    }

    public static int getSize(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    public static int getSize(Object[] objects) {
        return objects == null ? 0 : objects.length;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static <T> List<T> copyProperties(List<?> sources, Class<T> clazz) {
        if (isEmpty(sources)) { return new ArrayList<T>(0); }
        ArrayList<T> result = new ArrayList<T>(sources.size());
        for (Object object : sources) {
            T t = BeanUtils.instantiateClass(clazz);
            BeanUtils.copyProperties(object, t);
            result.add(t);
        }
        return result;
    }

    public static <T> List<T> copyProperties(Object[] sources, Class<T> clazz) {
        if (isEmpty(sources)) { return new ArrayList<T>(0); }
        ArrayList<T> result = new ArrayList<T>(sources.length);
        for (Object object : sources) {
            T t = null;
            try {
                t = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
             t = BeanUtils.instantiateClass(clazz);
            BeanUtils.copyProperties(object, t);
            result.add(t);
        }
        return result;
    }

    public static <T> T copyProperties(Object source, Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(source, t);
        return t;
    }
}
