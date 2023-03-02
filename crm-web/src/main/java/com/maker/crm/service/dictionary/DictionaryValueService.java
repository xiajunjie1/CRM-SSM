package com.maker.crm.service.dictionary;

import com.maker.crm.model.DictionaryValue;

import java.util.List;

public interface DictionaryValueService {
    public int addDicValue(DictionaryValue dictionaryValue)throws Exception;

    public List<DictionaryValue> queryAllDicValue()throws Exception;
}
