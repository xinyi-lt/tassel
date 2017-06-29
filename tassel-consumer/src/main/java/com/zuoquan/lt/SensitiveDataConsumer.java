package com.zuoquan.lt;

import com.touna.loan.sensitive.facade.dto.SensitiveDataDTO;
import com.touna.loan.sensitive.facade.intf.SensitiveDataFacade;
import com.zuoquan.lt.rpc.Result;

import java.util.List;

/**
 * Created by xinyi on 2017/6/28.
 */
public class SensitiveDataConsumer {

    private SensitiveDataFacade sensitiveDataFacade;

    public void setSensitiveDataFacade(SensitiveDataFacade sensitiveDataFacade) {
        this.sensitiveDataFacade = sensitiveDataFacade;
    }


    public List<SensitiveDataDTO> testDubboService(){
        Result<List<SensitiveDataDTO>> result = sensitiveDataFacade.getSensitiveDataList();

        return result.getData();
    }

}
