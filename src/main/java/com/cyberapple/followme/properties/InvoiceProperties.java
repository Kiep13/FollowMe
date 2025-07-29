package com.cyberapple.followme.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "invoice")
public record InvoiceProperties(Boolean enabled, String endpoint, String receiverId, String defaultCurrency) {
}
