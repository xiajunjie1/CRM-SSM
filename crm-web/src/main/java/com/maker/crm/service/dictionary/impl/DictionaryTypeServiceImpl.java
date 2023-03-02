package com.maker.crm.service.dictionary.impl;

import com.maker.crm.dao.dictionary.DictionaryTypeMapper;
import com.maker.crm.model.DictionaryType;
import com.maker.crm.service.dictionary.DictionaryTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService {
    private static final Logger LOGGER= LoggerFactory.getLogger(DictionaryTypeServiceImpl.class);
    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;
    @Override
    public DictionaryType queryDicTypeByCode(String code) throws Exception {
        if(!StringUtils.hasLength(code)){
            LOGGER.error("传入代码为空");
            return null;
        }
        DictionaryType type=null;
        type=dictionaryTypeMapper.selectDicTypeByCode(code);
        return type;
    }

    @Override
    public int addDicType(DictionaryType dictionaryType) throws Exception {
        if(dictionaryType==null){
            LOGGER.error("传入DictionaryType对象为空");
            return 0;
        }
        if(!StringUtils.hasLength(dictionaryType.getCode())){
            LOGGER.error("传入code为空");
            return 0;
        }
        if(!StringUtils.hasLength(dictionaryType.getName())){
            LOGGER.error("传入类型名称为空");
            return 0;
        }
        int result=dictionaryTypeMapper.insertDicType(dictionaryType);
        return result;
    }

    @Override
    public List<DictionaryType> queryAllDicType() throws Exception {

        return dictionaryTypeMapper.selectAllDicType();
    }
}
