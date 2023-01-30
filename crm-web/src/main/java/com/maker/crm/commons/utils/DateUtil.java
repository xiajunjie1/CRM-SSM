package com.maker.crm.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期与字符串转换工具类
 * */
public class DateUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(DateUtil.class);
    /**
     * yyyy-MM-dd HH:mm:ss
     * */
    public static String dateToDateTimeStr(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * yyyy-MM-dd
     * */
    public static String dateToDateStr(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * HH:mm:ss
     * */
    public static String dateToTimeStr(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static Date strToDate(String str){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            LOGGER.error("字符串转化为Date出错：{}",e.getMessage());
        }
        return null;
    }

    public static Date strToDateTime(String str){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            LOGGER.error("字符串转化为DateTime出错：{}",e.getMessage());
        }
        return null;
    }

    public static Date strToTime(String str){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            LOGGER.error("字符串转化为Time出错：{}",e.getMessage());
        }
        return null;
    }
}
