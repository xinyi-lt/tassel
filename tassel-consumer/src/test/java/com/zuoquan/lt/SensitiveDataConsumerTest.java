package com.zuoquan.lt;

import com.touna.loan.biz.dubbo.bean.Result;
import com.touna.loan.biz.exception.BizException;
import com.touna.loan.sensitive.facade.code.SensitiveDataType;
import com.touna.loan.sensitive.facade.dto.SensitiveDataDTO;
import com.zuoquan.lt.security.MD5Util;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinyi on 2017/6/28.
 */
public class SensitiveDataConsumerTest extends BaseTest {

    @Resource
    private SensitiveDataConsumer sensitiveDataConsumer;

    @Test
    public void testDubboService() throws BizException {
        Result<String> result = sensitiveDataConsumer.testDubboService("f576898b5dfc0d0ead8809cd6dd384bd4dda4fd6bb40b6a44607bc1f0e0c7036");
//        Result<String> result = sensitiveDataConsumer.testDubboService("18996321457");
        System.out.println(result.getData());
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void testDubboListService() throws BizException {
        List<String> list = new ArrayList<>();
        list.add("ee5f3459b2d3965b8e57c590a9d0ae423e570ac1e2579965b8f33c0a4938b6cd");
//        list.add("2362260896321478965");
        list.add(null);
        list.add("");
//        list.add("110213199206260012");
        list.add("b6b6883e5838b5f3e22832896f65fa10a8a7a9d09f13018d6021d70726839198");
        list.add("");
        list.add("094f5897a3092b53705c3b2c95aeb3d434a7a8f486d89d6a684136a730a1ba3d");
//        list.add("18996321457");
        Result result = sensitiveDataConsumer.testDubboListService(list);
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void testDubboDTOListService() throws BizException {
        List<SensitiveDataDTO> list = new ArrayList<>();
        SensitiveDataDTO dto1 = new SensitiveDataDTO();
        dto1.setDataType(SensitiveDataType.PHONE_NUMBER.getCode());
        dto1.setClearText("18688269907");

        list.add(null);
        list.add(dto1);
        list.add(null);

        SensitiveDataDTO dto2 = new SensitiveDataDTO();
        list.add(dto2);

        SensitiveDataDTO dto3 = new SensitiveDataDTO();
        dto3.setClearText("");
        list.add(dto3);

        SensitiveDataDTO dto4 = new SensitiveDataDTO();
        dto4.setDataType(SensitiveDataType.ID_NUMBER.getCode());
        dto4.setClearText("CNM1586971423690X");

        list.add(dto4);
        Result result = sensitiveDataConsumer.testDubboDTOListService(list);
        Assert.assertNotNull(result.getData());
    }

}
