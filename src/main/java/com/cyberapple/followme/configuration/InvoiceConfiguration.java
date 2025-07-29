package com.cyberapple.followme.configuration;

import com.cyberapple.followme.properties.InvoiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({InvoiceProperties.class})
//@Profile("invoices")
public class InvoiceConfiguration {
}
