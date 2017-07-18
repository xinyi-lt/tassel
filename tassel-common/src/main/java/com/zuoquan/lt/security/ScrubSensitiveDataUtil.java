package com.zuoquan.lt.security;

import org.springframework.util.StringUtils;

/**
 * Created by liutao on 2017/7/12.
 * 敏感信息（银行卡号，身份证号，手机号）用*代替
 */
public class ScrubSensitiveDataUtil {

    /**
     * 格式 14****19901012****
     * @param idNum
     * @return
     */
    public static String scrubIDNum(String idNum) {
        if (StringUtils.isEmpty(idNum)) {
            return "";
        }
        if (idNum.length() < 4) {
            return createStr(idNum.length(), '*');
        }
        if(idNum.length() >= 14) {
            return idNum.substring(0, 2) + createStr(4, '*') + idNum.substring(6, 14) + createStr(4, '*');
        }else {
            return idNum.substring(0, 4) + createStr(10, '*');
        }
    }

    /**
     * 银行卡号
     * @param bankCard
     * @return
     */
    public static String scrubBankCard(String bankCard) {
        if (StringUtils.isEmpty(bankCard)) {
            return "";
        }
        if (bankCard.length() < 9) {
            return createStr(bankCard.length(), '*');
        }
        return bankCard.substring(0, 4) + createStr(bankCard.length() - 8, '*')
                + bankCard.substring(bankCard.length() - 4, bankCard.length());
    }

    /**
     * 电话号
     * @param phoneNum
     * @return
     */
    public static String scrubPhone(String phoneNum){
        if (StringUtils.isEmpty(phoneNum)) {
            return "";
        }
        if (phoneNum.length() < 7){
            return phoneNum;
        }

        if (phoneNum.indexOf("-") > 0 ){
            return phoneNum.substring(0, phoneNum.length()-4) + "****";
        }

        return phoneNum.substring(0,3) + "****" + phoneNum.substring(7);
    }

    private static String createStr(int num, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
