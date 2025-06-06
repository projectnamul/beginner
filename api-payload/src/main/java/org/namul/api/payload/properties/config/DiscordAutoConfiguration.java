package org.namul.api.payload.properties.config;

import org.namul.api.payload.properties.DiscordProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties({DiscordProperties.class})
public class DiscordAutoConfiguration {
}
