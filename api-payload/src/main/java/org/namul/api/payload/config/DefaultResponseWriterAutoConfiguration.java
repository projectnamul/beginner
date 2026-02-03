package org.namul.api.payload.config;

import org.namul.api.payload.code.supports.DefaultBaseErrorCode;
import org.namul.api.payload.code.supports.DefaultBaseSuccessCode;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.namul.api.payload.writer.SuccessResponseWriter;
import org.namul.api.payload.writer.supports.DefaultFailureResponseWriter;
import org.namul.api.payload.writer.supports.DefaultSuccessResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DefaultResponseWriterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FailureResponseWriter.class)
    FailureResponseWriter<DefaultBaseErrorCode> defaultFailureResponseWriter() {
        return new DefaultFailureResponseWriter();
    }

    @Bean
    @ConditionalOnMissingBean(SuccessResponseWriter.class)
    SuccessResponseWriter<DefaultBaseSuccessCode> defaultSuccessResponseWriter() {
        return new DefaultSuccessResponseWriter();
    }
}
