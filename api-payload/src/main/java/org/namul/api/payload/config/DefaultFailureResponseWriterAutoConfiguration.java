package org.namul.api.payload.config;

import org.namul.api.payload.code.supports.DefaultBaseErrorCode;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.namul.api.payload.writer.supports.DefaultFailureResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DefaultFailureResponseWriterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FailureResponseWriter.class)
    FailureResponseWriter<DefaultBaseErrorCode> defaultFailureResponseWriter() {
        return new DefaultFailureResponseWriter();
    }

}
