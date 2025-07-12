package com.cyberapple.followme.configuration;

import com.linecorp.armeria.common.metric.NoopMeterRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ApiControllerUnitTestConfiguration {

    @Bean
    public MeterRegistry meterRegistry() {
        return NoopMeterRegistry.get(); // безопасная заглушка
    }
}
