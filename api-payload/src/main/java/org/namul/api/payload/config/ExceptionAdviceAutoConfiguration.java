package org.namul.api.payload.config;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.ExceptionAdvice;
import org.namul.api.payload.error.configurer.ExceptionAdviceConfigurer;
import org.namul.api.payload.log.DefaultExceptionAdviceLogger;
import org.namul.api.payload.log.ExceptionAdviceLogger;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.namul.api.payload.writer.supports.DefaultFailureResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ExceptionAdviceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FailureResponseWriter.class)
    DefaultFailureResponseWriter defaultFailureResponseWriter() {
        return new DefaultFailureResponseWriter();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionAdviceLogger.class)
    DefaultExceptionAdviceLogger defaultExceptionAdviceLogger() {
        return new DefaultExceptionAdviceLogger();
    }

    @Bean
    @ConditionalOnBean(ExceptionAdviceConfigurer.class)
    <R extends ErrorReasonDTO> ExceptionAdvice<R> exceptionAdvice(ExceptionAdviceConfigurer<R> exceptionAdviceConfigurer, ExceptionAdviceLogger exceptionAdviceLogger) {
        return new ExceptionAdvice<>(exceptionAdviceConfigurer, exceptionAdviceLogger);
    }
}
