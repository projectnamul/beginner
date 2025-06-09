package org.namul.api.payload.properties.config;

import org.namul.api.payload.properties.DiscordExceptionAdviceProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties({DiscordExceptionAdviceProperties.class})
public class DiscordAutoConfiguration {
}
