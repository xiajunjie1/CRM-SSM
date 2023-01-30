package com.maker.crm.config.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CustomerMapper extends ObjectMapper {
    private static final String Default_FAMATTER="yyyy-MM-dd MM:hh:ss";
    public CustomerMapper(){
        super.setDateFormat(new SimpleDateFormat(Default_FAMATTER));
        super.configure(SerializationFeature.INDENT_OUTPUT,true);//转换为格式化的JSON
        super.setSerializationInclusion(JsonInclude.Include.NON_NULL);//属性为Null，不序列化
        super.setTimeZone(TimeZone.getDefault());
    }
}
