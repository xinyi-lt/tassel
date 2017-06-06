package com.zuoquan.lt.basic.enums;

/**
 * Created by Administrator on 2017/6/6.
 */
public enum ServiceCode {
    SYSTEM_NORMAL(200, "系统正常"),
    //异常 返回码范围 400 -419 业务无关异常
    INVALID_PARAM(401,"无效参数"),
    //服务端异常
    SERVICE_ERROR(500, "服务异常");

    private int code;
    private String desc;

    ServiceCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
