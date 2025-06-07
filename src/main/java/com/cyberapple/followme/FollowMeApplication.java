package com.cyberapple.followme;

import com.cyberapple.followme.configuration.WebConfig;
import com.cyberapple.followme.configuration.SecurityConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import({WebConfig.class, SecurityConfig.class})
public class FollowMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowMeApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new org.springframework.orm.jpa.JpaTransactionManager();
	}
}
