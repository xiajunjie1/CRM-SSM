package com.maker.crm.config;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 事务配置类，同时也是切面类
 * */
@Configuration
@Aspect
public class TransactionManagerConfig {
    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionManagerConfig.class);
    @Bean
    public TransactionManager transactionManager(@Autowired DataSource dataSource){
        DataSourceTransactionManager transactionManager=new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
    /**
     * 增强类，txAdvice
     * */
    @Bean("txAdvice")
    public TransactionInterceptor transactionInterceptor(@Autowired TransactionManager transactionManager){
        TransactionInterceptor transactionInterceptor=new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        RuleBasedTransactionAttribute readAttribute=new RuleBasedTransactionAttribute();
        RuleBasedTransactionAttribute requiredAttribute=new RuleBasedTransactionAttribute();
        readAttribute.setReadOnly(true);
        readAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        requiredAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        Map<String, TransactionAttribute> mapAttribute=new HashMap<>();
        mapAttribute.put("add*",requiredAttribute);
        mapAttribute.put("save*",requiredAttribute);
        mapAttribute.put("edit*",requiredAttribute);
        mapAttribute.put("update*",requiredAttribute);
        mapAttribute.put("delete*",requiredAttribute);
        mapAttribute.put("remove*",requiredAttribute);
        mapAttribute.put("get*",readAttribute);
        mapAttribute.put("find*",readAttribute);
        mapAttribute.put("query*",readAttribute);
        NameMatchTransactionAttributeSource attributeSource=new NameMatchTransactionAttributeSource();
        attributeSource.setNameMap(mapAttribute);
        transactionInterceptor.setTransactionAttributeSource(attributeSource);
        return transactionInterceptor;
    }
    @Bean
    public Advisor transactionAdviceAdvisor(@Autowired TransactionInterceptor transactionInterceptor){
        AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * com.maker.crm..service..*.*(..))");
        LOGGER.info("织入事务！");
        return new DefaultPointcutAdvisor(pointcut,transactionInterceptor);
    }
}
