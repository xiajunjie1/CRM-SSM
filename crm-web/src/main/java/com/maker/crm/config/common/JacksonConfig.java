package com.maker.crm.config.common;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
/**
 * 配置Jackson组件，配置后，可以处理JSON请求，在接收参数时
 * 可以省略掉@RequestBody
 * */
@Configuration
public class JacksonConfig {
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
        RequestMappingHandlerAdapter adapter=new RequestMappingHandlerAdapter();
        MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        adapter.setMessageConverters(List.of(converter));
        return adapter;
    }
}
