package org.namul.api.payload.config;

import jakarta.validation.ConstraintViolationException;
import org.namul.api.payload.error.*;
import org.namul.api.payload.error.configurer.ExceptionAdviceConfigurer;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.handler.*;
import org.namul.api.payload.registry.ExceptionHandlerRegistry;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.namul.api.payload.writer.supports.DefaultFailureResponseWriter;
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
    @ConditionalOnMissingBean(FailureResponseWriter.class)
    DefaultFailureResponseWriter defaultFailureResponseWriter() {
        return new DefaultFailureResponseWriter();
    }

    @Bean
    @ConditionalOnClass(ConstraintViolationException.class)
    ConstraintViolationExceptionHandler constraintViolationExceptionHandler(FailureResponseWriter failureResponseWriter) {
        return new ConstraintViolationExceptionHandler(failureResponseWriter);
    }

    @Bean
    @ConditionalOnClass(MethodArgumentNotValidException.class)
    MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler(FailureResponseWriter failureResponseWriter) {
        return new MethodArgumentNotValidExceptionHandler(failureResponseWriter);
    }

    @Bean
    ServerApplicationExceptionHandler serverApplicationExceptionHandler(FailureResponseWriter failureResponseWriter) {
        return new ServerApplicationExceptionHandler(failureResponseWriter);
    }

    @Bean
    GlobalExceptionHandler globalExceptionHandler(FailureResponseWriter failureResponseWriter) {
        return new GlobalExceptionHandler(failureResponseWriter);
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
