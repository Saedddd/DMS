package com.example.dms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration class for SQL Server database connection
 * Activated only when "sqlserver" profile is active
 */
@Configuration
@Slf4j
@Profile("sqlserver")
public class SQLServerConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        
        // For Windows Authentication, username and password properties are not used
        // since authentication is handled by the Windows credentials
        
        log.info("SQL Server datasource configured with Windows Authentication");
        return dataSource;
    }
} 