package com.maker.crm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:config/database.properties")
public class HikariCPConfig {
    @Value("${mydb.database.driverClassName}")
    private String driverClassName;
    @Value("${mydb.database.jdbcUrl}")
    private String jdbcUrl;
    @Value("${mydb.database.username}")
    private String username;
    @Value("${mydb.database.password}")
    private String password;
    @Value("${mydb.database.connectionTimeOut}")
    private long connectionTimeOut;
    @Value("${mydb.database.readOnly}")
    private boolean readOnly;
    @Value("${mydb.database.pool.idleTimeOut}")
    private long idleTimeOut;
    @Value("${mydb.database.pool.maxLifeTime}")
    private long maxLifeTime;
    @Value("${mydb.database.pool.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${mydb.database.pool.minimumIdle}")
    private int minimumIdle;
    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource=new HikariDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setJdbcUrl(this.jdbcUrl);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setIdleTimeout(this.idleTimeOut);
        dataSource.setReadOnly(this.readOnly);
        dataSource.setMinimumIdle(this.minimumIdle);
        dataSource.setMaxLifetime(this.maxLifeTime);
        dataSource.setConnectionTimeout(this.connectionTimeOut);
        dataSource.setMaximumPoolSize(this.maximumPoolSize);
        return dataSource;
    }
}
