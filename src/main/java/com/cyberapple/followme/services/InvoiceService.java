package com.cyberapple.followme.services;

import com.cyberapple.followme.properties.InvoiceProperties;
import com.cyberapple.followme.records.Invoice;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    private WebClient webClient = WebClient.create();

    private final InvoiceProperties invoiceProperties;

    public void performInvoiceRequest(Integer amountOfMoney, String description) {
        if(!invoiceProperties.enabled()) {
            logger.info("Invoice service is disabled, skipping request.");
            return;
        }

        Invoice invoice = new Invoice(
                invoiceProperties.receiverId(),
                amountOfMoney,
                invoiceProperties.defaultCurrency(),
                description
        );

        webClient
                .post()
                .uri(invoiceProperties.endpoint())
                .bodyValue(invoice)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        logger.info("Invoice request sent successfully: {}", invoice);
                        return response.bodyToMono(Void.class);
                    } else {
                        logger.error("Error sending invoice request: {}", invoice);
                        return response.createException().flatMap(Mono::error);
                    }
                });
    }
}
