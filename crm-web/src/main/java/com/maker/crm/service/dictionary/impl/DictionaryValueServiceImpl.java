package com.maker.crm.service.dictionary.impl;

import com.maker.crm.dao.dictionary.DictionaryValueMapper;
import com.maker.crm.model.DictionaryValue;
import com.maker.crm.service.dictionary.DictionaryValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DictionaryValueServiceImpl implements DictionaryValueService {
    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(DictionaryValueServiceImpl.class);
    @Override
    public int addDicValue(DictionaryValue dictionaryValue) throws Exception {
    if(dictionaryValue==null){
        LOGGER.error("传入的字典值参数为空");
        return 0;
    }
    if(!StringUtils.hasLength(dictionaryValue.getValue())){
        LOGGER.error("传入的字典值value为空");
        return 0;
    }
    if(!StringUtils.hasLength(dictionaryValue.getText())){
        LOGGER.error("传入的字典值text为空");
        return 0;
    }
        return dictionaryValueMapper.insertDictionaryValue(dictionaryValue);
    }

    @Override
    public List<DictionaryValue> queryAllDicValue() throws Exception {
        List<DictionaryValue> dictionaryValueList=null;

            dictionaryValueList=dictionaryValueMapper.selectAllDictionaryValue();



        return dictionaryValueList;
    }

    @Override
    public List<DictionaryValue> queryDicValueByTypeCode(String typeCode) throws Exception {
        if(!StringUtils.hasLength(typeCode)){
            LOGGER.error("传入的typeCode值为空");
            return null;
        }
        return dictionaryValueMapper.selectDictionaryValuesByTypeCode(typeCode);
    }
}
