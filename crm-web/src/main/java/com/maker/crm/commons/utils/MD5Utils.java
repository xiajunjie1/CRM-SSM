package com.maker.crm.commons.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5Utils {
    public static String encrypt(String pwd){
        return DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
    }
}
