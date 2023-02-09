package com.muhammadusman92.userservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UserServiceAspect {
    @Before("within(com.muhammadusman92.userservice..*)")
    public void beforeLog(JoinPoint joinPoint){

        log.info("Before "+joinPoint.getSignature()+"of User-Service");
    }
    @AfterReturning("within(com.muhammadusman92.userservice..*)")
    public void afterLog(JoinPoint joinPoint){
        log.info("After "+joinPoint.getSignature()+"of User-Service");
    }
}
