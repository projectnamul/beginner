package org.namu.api.payload.config;

import jakarta.validation.ConstraintViolationException;
import org.namu.api.payload.error.*;
import org.namu.api.payload.handler.ConstraintViolationExceptionHandler;
import org.namu.api.payload.handler.GlobalExceptionHandler;
import org.namu.api.payload.handler.MethodArgumentNotValidExceptionHandler;
import org.namu.api.payload.handler.ServerApplicationExceptionHandler;
import org.namu.api.payload.registry.ExceptionHandlerRegistry;
import org.namu.api.payload.util.ResponseWriteUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@AutoConfiguration
public class ExceptionAdviceAutoConfiguration {

    @Bean
    @ConditionalOnClass(ConstraintViolationException.class)
    ConstraintViolationExceptionHandler constraintViolationExceptionHandler(ResponseWriteUtil responseWriteUtil ) {
        return new ConstraintViolationExceptionHandler(responseWriteUtil);
    }

    @Bean
    @ConditionalOnClass(MethodArgumentNotValidException.class)
    MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler(ResponseWriteUtil responseWriteUtil ) {
        return new MethodArgumentNotValidExceptionHandler(responseWriteUtil);
    }

    @Bean
    ServerApplicationExceptionHandler serverApplicationExceptionHandler(ResponseWriteUtil responseWriteUtil ) {
        return new ServerApplicationExceptionHandler(responseWriteUtil);
    }

    @Bean
    GlobalExceptionHandler globalExceptionHandler(ResponseWriteUtil responseWriteUtil ) {
        return new GlobalExceptionHandler(responseWriteUtil);
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionHandlerRegistry.class)
    ExceptionHandlerRegistry exceptionHandlerRegistry(Optional<ConstraintViolationExceptionHandler> constraintViolationExceptionHandler,
                                                      Optional<MethodArgumentNotValidExceptionHandler> methodArgumentNotValidExceptionHandler,
                                                      Optional<ServerApplicationExceptionHandler> serverApplicationExceptionHandler,
                                                      GlobalExceptionHandler globalExceptionHandler) {
        ExceptionHandlerRegistry exceptionHandlerRegistry = new ExceptionHandlerRegistry();
        constraintViolationExceptionHandler.ifPresent(handler -> exceptionHandlerRegistry.addHandler(ConstraintViolationException.class, handler));
        methodArgumentNotValidExceptionHandler.ifPresent(handler -> exceptionHandlerRegistry.addHandler(MethodArgumentNotValidException.class, handler));
        serverApplicationExceptionHandler.ifPresent(handler -> exceptionHandlerRegistry.addHandler(ServerApplicationException.class, handler));
        exceptionHandlerRegistry.addHandler(Exception.class, globalExceptionHandler);
        return exceptionHandlerRegistry;
    }

    @Bean
    @ConditionalOnBean(ExceptionAdviceConfigurer.class)
    @ConditionalOnClass(ConstraintViolationException.class)
    ConstraintViolationExceptionAdvice constraintViolationExceptionAdvice(ExceptionAdviceConfigurer configurer) {
        return configurer.getConstraintViolationExceptionAdvice();
    }

    @Bean
    @ConditionalOnBean(ExceptionAdviceConfigurer.class)
    @ConditionalOnClass(MethodArgumentNotValidException.class)
    MethodArgumentNotValidExceptionAdvice methodArgumentNotValidExceptionAdvice(ExceptionAdviceConfigurer configurer) {
        return configurer.getMethodArgumentNotValidExceptionAdvice();
    }

    @Bean
    @ConditionalOnBean(ExceptionAdviceConfigurer.class)
    ServerApplicationExceptionAdvice serverApplicationExceptionAdvice(ExceptionAdviceConfigurer configurer) {
        return configurer.getServerApplicationExceptionAdvice();
    }

    @Bean
    @ConditionalOnBean(ExceptionAdviceConfigurer.class)
    GlobalExceptionAdvice advices(ExceptionAdviceConfigurer configurer) {
        return configurer.getGlobalExceptionAdvice();
    }
}
