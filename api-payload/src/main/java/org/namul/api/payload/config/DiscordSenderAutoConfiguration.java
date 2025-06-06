package org.namul.api.payload.config;

import org.namul.api.payload.message.DiscordExceptionAdviceMessageSender;
import org.namul.api.payload.message.DiscordExceptionAdviceUtil;
import org.namul.api.payload.properties.DiscordProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DiscordSenderAutoConfiguration {

    @Bean
    DiscordExceptionAdviceUtil discordUtil() {
        return new DiscordExceptionAdviceUtil();
    }

    @Bean
    DiscordExceptionAdviceMessageSender discordExceptionAdviceMessageSender(DiscordProperties discordProperties) {
        return new DiscordExceptionAdviceMessageSender(discordUtil(), discordProperties);
    }
}
