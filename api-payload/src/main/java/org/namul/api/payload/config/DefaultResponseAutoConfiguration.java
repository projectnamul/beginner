package org.namul.api.payload.config;

import org.namul.api.payload.writer.ResponseWriter;
import org.namul.api.payload.util.DefaultResponseWriteUtil;
import org.namul.api.payload.util.ResponseWriteUtil;
import org.namul.api.payload.writer.DefaultResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class DefaultResponseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResponseWriter.class)
    ResponseWriter responseWriter() {
        return new DefaultResponseWriter(defaultResponseWriteUtil());
    }

    @Bean
    @ConditionalOnMissingBean(ResponseWriteUtil.class)
    ResponseWriteUtil defaultResponseWriteUtil() {
        return new DefaultResponseWriteUtil();
    }

}
