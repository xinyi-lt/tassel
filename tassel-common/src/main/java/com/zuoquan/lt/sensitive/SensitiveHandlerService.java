package com.zuoquan.lt.sensitive;

import org.springframework.stereotype.Service;

//import com.touna.loan.sensitive.facade.code.SensitiveDataServiceCode;
//import com.touna.loan.sensitive.facade.intf.SensitiveDataFacade;

/**
 * Created by liutao on 2017/7/7.
 */
@Service
public class SensitiveHandlerService<T> extends DefaultSensitiveHandler {

//    @Resource
//    private SensitiveDataFacade sensitiveDataFacade;
//
//    public void setClearTextForSensitiveField(T obj, Class<T> clazz) throws IllegalAccessException, BizException {
//        List<String>  cipherList = setSensitiveDataForObject(obj, clazz);
//
//        Result<Map<String, String>> result = sensitiveDataFacade.getClearTextByCipherList(cipherList);
//        if(SensitiveDataServiceCode.SYSTEM_NORMAL.getCode() == result.getStatus()){
//            Map<String, String> returnMap = result.getData();
//
//            Field[] fields = clazz.getDeclaredFields();
//
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(SensitiveField.class)){
//                    field.setAccessible(true);
//                    //敏感字段都为string类型
//                    String fieldValue = (String)field.get(obj);
//
//                    String clearText = returnMap.get(fieldValue);
//                    if(null != clearText && !clearText.trim().equals("")){
//                        field.set(obj, clearText);
//                    }
//                }
//            }
//        }
//
//    }
}
