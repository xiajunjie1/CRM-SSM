package com.maker.crm.service.abs;

import org.springframework.util.StringUtils;

public class AbstractService {
    protected boolean isNull(String param){
        return !StringUtils.hasLength(param);//长度为0返回true
    }
}
