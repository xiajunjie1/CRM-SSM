package com.maker.crm.commons.utils;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.ResourceBundle;

public class PropertiesUtil  {
    private  String fileName="Possibility";
    private ResourceBundle resourceBundle;
    public PropertiesUtil(){
        resourceBundle=ResourceBundle.getBundle(fileName);
    }
    public PropertiesUtil(String fileName){
        this.fileName=fileName;
    }

    public String getValue(String key){
        return resourceBundle.getString(key);
    }
}
