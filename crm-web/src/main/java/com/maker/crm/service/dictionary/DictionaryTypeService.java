package com.maker.crm.service.dictionary;

import com.maker.crm.model.DictionaryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public interface DictionaryTypeService {

    public DictionaryType queryDicTypeByCode(String code)throws Exception;
    public int addDicType(DictionaryType dictionaryType)throws Exception;

    public List<DictionaryType> queryAllDicType()throws Exception;

}
