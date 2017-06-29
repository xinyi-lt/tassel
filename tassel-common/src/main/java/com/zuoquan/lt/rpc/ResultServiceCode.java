package com.zuoquan.lt.rpc;

/**
 * @author: xinyi
 */
public enum ResultServiceCode implements ServiceCode {

    SYSTEM_NORMAL(200, "系统正常"),

    // --------------------------------------------------------------//

    //客户端异常 返回码范围 400 -419 业务无关异常
    INVALID_PARAM(401,"无效参数"),
    DATA_NOT_EXIST(402, "数据不存在或查询为空"),
    DUPLICATE_COMMIT_ERROR(403, "重复操作异常"),


    //客户端异常 返回码范围 420 -499 业务相关异常
    BUSSINESS_RULE_CHECK_FAILT(420,"业务规则校验不通过"),
    SAVE_APPLY_VALIDATE_ERROR(421, "申请保存校验异常"),
    BUSSINESS_RESULT_CUSTOM_CHECK_SALESMAN_PROTECT(422, "自定义需要进入客户经理校验"),


    // --------------------------------------------------------------//
    //服务端异常
    SERVICE_ERROR(500, "服务错误");


    private int code;
    private String desc;

    ResultServiceCode(int code, String desc) {
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
