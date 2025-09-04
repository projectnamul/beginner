package org.namul.api.payload.config;

import org.namul.api.payload.writer.FailureResponseWriter;
import org.namul.api.payload.writer.supports.DefaultFailureResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ExceptionAdviceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FailureResponseWriter.class)
    FailureResponseWriter defaultFailureResponseWriter() {
        return new DefaultFailureResponseWriter();
    }

}
