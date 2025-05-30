package org.namu.api.payload.config;

import org.namu.api.payload.writer.ResponseWriter;
import org.namu.api.payload.util.DefaultResponseWriteUtil;
import org.namu.api.payload.util.ResponseWriteUtil;
import org.namu.api.payload.writer.DefaultResponseWriter;
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
