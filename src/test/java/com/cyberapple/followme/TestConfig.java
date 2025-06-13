package com.cyberapple.followme;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({FollowMeApplication.class})
public class TestConfig {
}