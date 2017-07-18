package com.zuoquan.lt.sensitive;

import java.util.List;

/**
 * Created by liutao on 2017/7/6.
 */
public interface SensitiveHandler<T> {
    /**
     * 设置对象敏感字段，有密文转为明文(显示)
     * @param obj
     */
    List<String> setSensitiveDataForObject(T obj, Class<T> clazz) throws IllegalAccessException;

    /**
     * 设置列表敏感字段，有密文转为明文(显示)
     * @param obj
     */
    void setSensitiveDataForList(List<T> obj, Class<T> clazz);
}
