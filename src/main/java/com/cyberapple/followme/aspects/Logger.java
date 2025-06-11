package com.cyberapple.followme.aspects;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cyberapple.followme.services.LogService;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Order(1)
@Component
@AllArgsConstructor
public class Logger {
    private final LogService logService;

    @Before("execution(* com.cyberapple.followme.api.controllers.**.*(..))")
    public void logApiCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String timestamp = getTimestamp();

        String message = String.format("[%s] API call to: %s", timestamp, methodName);
        logService.log(message);
    }

    private String getTimestamp() {
        Instant now = Instant.now();
        
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        
        return formatter.format(now);
    }
}