package com.maker.crm.config.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class ViewResolverConfig {

    @Bean
    public InternalResourceViewResolver resourceViewResolver(){
        InternalResourceViewResolver resolver=new InternalResourceViewResolver();
        resolver.setSuffix(".jsp");
        resolver.setPrefix("/WEB-INF/pages");
        return resolver;
    }
}
