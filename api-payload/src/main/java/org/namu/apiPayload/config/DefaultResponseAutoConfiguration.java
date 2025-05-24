package org.namu.apiPayload.config;

import org.namu.apiPayload.util.DefaultResponseWriteUtil;
import org.namu.apiPayload.util.ResponseWriteUtil;
import org.namu.apiPayload.writer.DefaultResponseWriter;
import org.namu.apiPayload.writer.ResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DefaultResponseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResponseWriter.class)
    ResponseWriter responseWriter() {
        return new DefaultResponseWriter(responseWriteUtil());
    }

    @Bean
    @ConditionalOnMissingBean(ResponseWriteUtil.class)
    DefaultResponseWriteUtil responseWriteUtil() {
        return new DefaultResponseWriteUtil();
    }
}
