package com.cyberapple.followme;

import com.cyberapple.followme.configuration.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import({WebConfig.class})
public class FollowMeApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(FollowMeApplication.class, args);
//		ExcursionService excursionService = applicationContext.getBean("excursionService",ExcursionService.class);
//
//		Iterable<ExcursionDto> excursions = excursionService.getAllExcursions();
//		for (ExcursionDto excursion : excursions) {
//			System.out.println(excursion.getId() + ": " + excursion.getTitle());
//		}
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new org.springframework.orm.jpa.JpaTransactionManager();
	}
}
