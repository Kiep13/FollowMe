package com.cyberapple.followme.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private Logger logger = LoggerFactory.getLogger(LogService.class);

    public void log(String message) {
        logger.info(message);
        System.out.println(message);
    }   
}
