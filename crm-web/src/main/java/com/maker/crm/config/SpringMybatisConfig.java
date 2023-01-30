package com.maker.crm.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@Configuration
public class SpringMybatisConfig {
    /**
     * 注册Spring整合Mybatis的核心类
     * */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource){
        SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
        ResourceLoader resourceLoader=new DefaultResourceLoader();
        Resource resource=resourceLoader.getResource("classpath:config/mybatis-config.xml");
        factoryBean.setConfigLocation(resource);
        factoryBean.setDataSource(dataSource);//必须设置
        return factoryBean;
    }
    /**
     * 配置mybatis注解扫描配置，扫描@MapperScanner注解，为dao接口生成代理类注册到Spring中
     * */
    @Bean
    public MapperScannerConfigurer mapperScanner(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.maker.crm.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }


}
