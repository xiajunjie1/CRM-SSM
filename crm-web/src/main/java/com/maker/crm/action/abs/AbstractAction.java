package com.maker.crm.action.abs;

import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditor;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AbstractAction {
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   @Autowired
    protected HttpServletRequest request;
   @Autowired
   protected HttpServletResponse response;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(java.util.Date.class,new PropertiesEditor(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                LocalDateTime localDateTime=LocalDateTime.parse(text,LOCAL_DATE_FORMATTER);
                ZonedDateTime zonedDateTime=localDateTime.atZone(ZoneId.systemDefault());
                Instant instant=zonedDateTime.toInstant();
                super.setValue(java.util.Date.from(instant));
            }
        });
    }

    public boolean notNull(String param){
        return StringUtils.hasLength(param);
    }
}
