package com.zuoquan.lt;

import com.touna.loan.sensitive.facade.dto.SensitiveDataDTO;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xinyi on 2017/6/28.
 */
public class SensitiveDataConsumerTest extends BaseTest {

    @Resource
    private SensitiveDataConsumer sensitiveDataConsumer;

    @Test
    public void testDubboService(){
        List<SensitiveDataDTO> list = sensitiveDataConsumer.testDubboService();
        Assert.assertNotNull(list);
    }
}
