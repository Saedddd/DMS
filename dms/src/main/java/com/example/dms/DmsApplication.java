package com.example.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@Slf4j
public class DmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

	@Bean
	public CommandLineRunner logDatabaseInfo(Environment env) {
		return args -> {
			log.info("===========================================");
			log.info("Database connection information:");
			log.info("URL: {}", env.getProperty("spring.datasource.url"));
			log.info("Driver: {}", env.getProperty("spring.datasource.driver-class-name"));
			log.info("Database platform: {}", env.getProperty("spring.jpa.database-platform"));
			log.info("Using SQLite embedded database");
			log.info("===========================================");
		};
	}
}
