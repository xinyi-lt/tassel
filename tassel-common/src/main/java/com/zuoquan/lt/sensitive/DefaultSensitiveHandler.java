package com.zuoquan.lt.sensitive;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutao on 2017/7/6.
 */
public class DefaultSensitiveHandler<T> implements SensitiveHandler<T> {

    public List<String> setSensitiveDataForObject(T obj, Class<T> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        List<String> cipherList = new ArrayList<String>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(SensitiveField.class)){
                field.setAccessible(true);
                //敏感字段都为string类型
                String fieldValue = (String)field.get(obj);

                if(null != fieldValue && !fieldValue.trim().equals("")){
                    //属性值不为空
                    cipherList.add(fieldValue);
                }
            }
        }

        return cipherList;
    }

    public void setSensitiveDataForList(List<T> obj, Class<T> clazz) {

    }
}
