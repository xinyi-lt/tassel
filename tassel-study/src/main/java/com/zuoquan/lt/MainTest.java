package com.zuoquan.lt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zuoquan.lt.basic.util.DateUtil;
import com.zuoquan.lt.basic.util.HttpClientUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by liutao on 2018/4/17.
 */
public class MainTest {
    public static void main(String[] args) throws ParseException, UnsupportedEncodingException, InterruptedException {

//        BigDecimal test1 = new BigDecimal(1.01);
//        BigDecimal test2 = BigDecimal.valueOf(1.01);
//        System.out.println(test1);
//        System.out.println(test2);

        BigDecimal totalManageFee = BigDecimal.valueOf(85233.85);
//        BigDecimal percent = totalManageFee.divide(BigDecimal.valueOf(100000), 6, BigDecimal.ROUND_HALF_UP)
//                .multiply(BigDecimal.valueOf(100)).setScale(0,BigDecimal.ROUND_DOWN);

//        System.out.println(totalManageFee.setScale(0,BigDecimal.ROUND_CEILING));
//
//        Integer applyId = 99184042;
//        String unifiedApplyId = "99184042";
//
//
//        if (applyId == Integer.valueOf(unifiedApplyId)){
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }


//        Integer a = new Integer(321);
//        Integer c = 321;
//        Integer d = 321;
//        System.out.println(a==321);
//        System.out.println(a==c);
//
//        System.out.println(d==c);

//        String dateStr = "2018-3-1";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate = format.parse(dateStr);
////        System.out.println(format.format(startDate));
////
//        int accountDay = DateUtil.getDayDate(DateUtil.addDay(startDate, -1));
//
//        if(DateUtil.getDayDate(startDate) == 1) {
//            accountDay = 31;
//        }
//
//
//
//        System.out.println(accountDay);
//
//        Date endDate = DateUtil.getEndDate(DateUtil.addMonth(date, 0), accountDay);
//
//        System.out.println(format.format(endDate));

//        if(DateUtil.getDayDate(date) == 1) {
//            pBalanceday = 31;
//        }
//
//        if(DateUtil.getDayDate(pStartdate) == 31)
//        {
//            pBalanceday = 30;
//        }


//         Date endDate = DateUtils.addMonths(date,1);
//         System.out.println(format.format(endDate));
//        int accountDay = DateUtil.getDayDate(DateUtil.addDay(startDate, -1));
//        Date startDateLoop = null;
//        Date endDateLoop = null;
//        for (int termLoop = 0; termLoop <= 12; termLoop++) {
//            if (termLoop == 0) {
//                //第0期
//                startDateLoop = startDate;
//                endDateLoop = startDate;
//            } else {
//                int termTemp = termLoop;
//
//                if(DateUtil.getDayDate(startDate) == 1)
//                {
//                    termTemp = termLoop -1;
//                }
//
//                if (termLoop == 1) {
//                    startDateLoop = startDate;
//                } else {
//                    startDateLoop = DateUtil.addDay(endDateLoop, 1);
//                }
//                // 计息天数
//                endDateLoop = DateUtil.getEndDate(DateUtil.addMonth(startDate, termTemp), accountDay);
////                interestDaysLoop = DateUtil.dateDiff(endDateLoop, startDateLoop) + 1;
//
//            }
//            System.out.println(format.format(startDateLoop) + ", " + format.format(endDateLoop));
//        }

//        for (int termLoop = 0; termLoop <= 12; termLoop++) {
//            if (termLoop == 0){
//                //第0期
//                startDateLoop = startDate;
//                endDateLoop = startDate;
//            }else {
//                startDateLoop = DateUtils.addMonths(startDate, termLoop - 1);
//                endDateLoop = DateUtils.addDays(DateUtils.addMonths(startDate, termLoop), -1);
//                // 计息天数
////                interestDaysLoop = DateUtil.dateDiff(endDateLoop, startDateLoop) + 1;
//
//            }
//            System.out.println(format.format(startDateLoop) + ", " + format.format(endDateLoop));
//        }


//        httpTest();
        requestRateLimiterTest();
    }

    public static final String URL = "http://10.0.4.69:9002/fee-calculate/get-loan-amount";

    public static void httpTest() throws UnsupportedEncodingException {
        JSONObject paramJson = new JSONObject();
        paramJson.put("applyAmount", BigDecimal.valueOf(50000));
        paramJson.put("repaymentWay", 4);
        paramJson.put("loanTerm", 12);
        paramJson.put("loanType", 1);
        paramJson.put("rate", BigDecimal.valueOf(0.75));
        paramJson.put("applyId", 75395142);
        paramJson.put("productCode", "P013");
        paramJson.put("salesDept", "西安服务商");


        JSONObject invokeResult = JSONObject.parseObject(HttpClientUtil.httpPost(URL, null, JSON.toJSONString(paramJson)));

        System.out.println(invokeResult.toString());
        System.out.println(invokeResult.get("data"));
    }

    public static final String RequestRateLimiter = "http://localhost:9003/fee-config/demo/hello?name=niak&token=0993";


    public static void requestRateLimiterTest() throws UnsupportedEncodingException, InterruptedException {
        //限流测试
        for (int i = 0; i < 20; i++) {
            String result = HttpClientUtil.httpGet(RequestRateLimiter, null, null);

            System.out.println(result + i);
            if (i == 10){
                //睡500毫秒
                TimeUnit.MILLISECONDS.sleep(1200);
            }
        }

    }
}
