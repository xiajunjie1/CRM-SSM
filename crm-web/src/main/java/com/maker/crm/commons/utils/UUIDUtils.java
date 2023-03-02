package com.maker.crm.commons.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 返回32位UUID
     * */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
