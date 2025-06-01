package org.namul.api.payload.config;

import org.namul.api.payload.writer.SuccessResponseWriter;
import org.namul.api.payload.writer.supports.DefaultSuccessResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class DefaultResponseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SuccessResponseWriter.class)
    DefaultSuccessResponseWriter responseWriter() {
        return new DefaultSuccessResponseWriter();
    }

}
