package com.cyberapple.followme;

import com.cyberapple.followme.services.ExcursionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("test")
class FollowMeApplicationTests {

	@BeforeEach
	void setUp() {
		System.out.println("Base test setup: Executing before each test");
	}

	@Test
	void contextLoads(@Autowired ExcursionService excursionService) {
		assert (excursionService != null);
	}
}