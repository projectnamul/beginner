package org.namul.api.payload.properties.config;

import org.namul.api.payload.properties.SlackExceptionAdviceProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties({SlackExceptionAdviceProperties.class})
public class SlackAutoConfiguration {
}
