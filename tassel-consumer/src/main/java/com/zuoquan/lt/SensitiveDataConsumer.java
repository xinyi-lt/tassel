package com.zuoquan.lt;

import com.touna.loan.sensitive.facade.dto.SensitiveDataDTO;
import com.touna.loan.sensitive.facade.intf.SensitiveDataFacade;

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
        List<SensitiveDataDTO> list = sensitiveDataFacade.getSensitiveDataList();
        return list;
    }

}
