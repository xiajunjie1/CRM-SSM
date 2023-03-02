package com.maker.crm.context.config;

import com.maker.crm.action.interceptor.LoginValidateInterceptor;
import com.mysql.cj.log.Log;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({"com.maker.crm.action"})
public class WebApplicationConfig implements WebMvcConfigurer {

    /**
     * 配置静态资源放行
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("/image/");
        registry.addResourceHandler("/jquery/**").addResourceLocations("/jquery/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginValidateInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/login_handle")
                .excludePathPatterns("/errors/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/jquery/**");

    }
    /**
     * 配置Multipart解析器
     * 上传文件时需要配置该解析器，SpringMVC内部会调用该对象进行上传文件的解析接收
     * 此对象配置的id必须为multipartResolver
     * */
    @Bean("multipartResolver")
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024*1024*5);//最大上传文件5M
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }
}
