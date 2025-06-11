package com.cyberapple.followme.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cyberapple.followme.services.LogService;

import lombok.AllArgsConstructor;

import java.time.LocalTime;

@Aspect
@Order(2)
@Component
@AllArgsConstructor
public class TimeTracker {
    private final LogService logService;

    @Around("execution(* com.cyberapple.followme.api.controllers.**.*(..))")
    public Object logTimeCall(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        
        LocalTime startTime = LocalTime.now();

        var value = point.proceed();

        LocalTime endTime = LocalTime.now();

        String message = String.format("Method %s finished in %d nanoseconds",
                methodName,
                endTime.getNano() - startTime.getNano());
        logService.log(message);

        return value;
    }
}
