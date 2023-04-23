package com.project.hafaly_be.domain.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAOP{
    private Logger logger = LoggerFactory.getLogger(LoggerAOP.class);
    @After("execution(* com.project.hafaly_be.api.exception.HandlerException.handlerInvalidArgumentException())")
    public void afterHandlerEmailNotValidException(JoinPoint joinPoint){
        logger.error(joinPoint.toString());
    }
    @Before("execution(* com.project.hafaly_be.api.controller.AuthController.login(com.project.hafaly_be.api" +
            ".dto.RequestLogin, org.springframework.validation.BindingResult))")
    public void beforeRegister(JoinPoint joinPoint){
        logger.info("Bat dau dang nhap, " + joinPoint.toString());
    }
}
