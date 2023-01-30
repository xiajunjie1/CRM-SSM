package com.maker.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeTest {
    private static final Logger LOGGER= LoggerFactory.getLogger(TimeTest.class);
    @Test
    public void timeTest(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time=LocalDateTime.parse("2022-12-31 10:10:10",formatter);
        ZonedDateTime zonedDateTime=time.atZone(ZoneId.systemDefault());
        Instant instant=zonedDateTime.toInstant();
        Date date=java.util.Date.from(instant);
        LOGGER.info("日期为：{}",date);
    }
}
