package com.maker.crm.context.config;

import com.maker.crm.action.interceptor.LoginValidateInterceptor;
import com.mysql.cj.log.Log;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
}
