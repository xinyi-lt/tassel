package com.zuoquan.lt;

import com.touna.loan.biz.dubbo.bean.Result;
import com.touna.loan.biz.exception.BizException;
import com.touna.loan.sensitive.facade.code.SensitiveDataType;
import com.touna.loan.sensitive.facade.dto.SensitiveDataDTO;
import com.touna.loan.sensitive.facade.intf.SensitiveDataFacade;

import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by xinyi on 2017/6/28.
 */
public class SensitiveDataConsumer {

    private SensitiveDataFacade sensitiveDataFacade;

    public void setSensitiveDataFacade(SensitiveDataFacade sensitiveDataFacade) {
        this.sensitiveDataFacade = sensitiveDataFacade;
    }


    public Result testDubboService(String cipherText) throws BizException {
        Result<String> result = sensitiveDataFacade.getClearTextByCipher(cipherText);
//        Result<String> result = sensitiveDataFacade.getMosaicTextByCipher(cipherText);
//        Result<String> result = sensitiveDataFacade.saveSensitiveData(cipherText, SensitiveDataType.PHONE_NUMBER.getCode());

        return result;
    }

    public Result testDubboListService(List<String> list) throws BizException {
//        Result<Map<String, String>> result = sensitiveDataFacade.getClearTextByCipherList(list);
        Result<Map<String, String>> result = sensitiveDataFacade.getMosaicTextByCipherList(list);

        return result;
    }

    public Result testDubboDTOListService(List<SensitiveDataDTO> list) throws BizException {
        Result<Map<String, String>> result = sensitiveDataFacade.saveSensitiveDataBatch(list);

        return result;
    }

}
