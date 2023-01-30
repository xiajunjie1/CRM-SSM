package com.maker.crm.context.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({"com.maker.crm.service","com.maker.crm.config"})
@EnableAspectJAutoProxy //一定要添加此注解，否则事务不生效
public class ApplicationConfig {
}
