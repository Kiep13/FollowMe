package com.cyberapple.followme.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class Tracker {
    private Logger logger = LoggerFactory.getLogger(Tracker.class);

    @Before("execution(* com.cyberapple.followme.api.controllers.ApiExcursionController.*(..))")
    public void logBeforeGetAllExcursions() {
        log("Excursion api called");
    }

    @AfterReturning("execution(* com.cyberapple.followme.api.controllers.ApiExcursionController.*(..))")
    public void logAfterGetAllExcursions() {
        log("Excursion api returned value");
    }

    @Around("execution(* com.cyberapple.followme.api.controllers.ApiExcursionController.*(..))")
    public Object logTimeCall(ProceedingJoinPoint point) throws Throwable {
        LocalTime startTime = LocalTime.now();

        var value = point.proceed();

        LocalTime endTime = LocalTime.now();

        log("Execution time: " + (endTime.getNano() - startTime.getNano()) + " nanoseconds");

        return value;
    }

    private void log(String message) {
        logger.info(message);
        System.out.println(message);
    }
}
