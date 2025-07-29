package com.cyberapple.followme.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "invoices")
public record InvoiceProperties(Boolean enabled, String endpoint, String receiverId, String defaultCurrency) {
}
