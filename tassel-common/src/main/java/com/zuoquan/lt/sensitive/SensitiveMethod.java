package com.zuoquan.lt.sensitive;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liutao on 2017/6/16.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveMethod {

    String value() default "";

    MethodType methodType();

//    SensitiveDataType returnType() default SensitiveDataType.REFERENCE;

//    Class returnType();
}
