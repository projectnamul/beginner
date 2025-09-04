package org.namul.api.payload.config;

import org.namul.api.payload.error.handler.supports.DiscordMessageSenderAdditionalExceptionHandler;
import org.namul.api.payload.message.DiscordExceptionAdviceMessage;
import org.namul.api.payload.message.DiscordExceptionAdviceMessageSender;
import org.namul.api.payload.message.DiscordExceptionAdviceUtil;
import org.namul.api.payload.message.generator.DefaultDiscordExceptionAdviceMessageGenerator;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;
import org.namul.api.payload.properties.DiscordExceptionAdviceProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@AutoConfiguration
public class DiscordSenderAutoConfiguration {

    @Bean
    DiscordExceptionAdviceUtil discordUtil() {
        return new DiscordExceptionAdviceUtil();
    }

    @Bean
    DiscordExceptionAdviceMessageSender discordExceptionAdviceMessageSender(DiscordExceptionAdviceProperties discordExceptionAdviceProperties) {
        return new DiscordExceptionAdviceMessageSender(discordUtil(), discordExceptionAdviceProperties);
    }

    @Bean
    @ConditionalOnMissingBean(DiscordMessageSenderAdditionalExceptionHandler.class)
    DiscordMessageSenderAdditionalExceptionHandler discordMessageSenderAdditionalExceptionHandler(
            DiscordExceptionAdviceProperties discordExceptionAdviceProperties,
            Optional<ExceptionAdviceMessageGenerator<DiscordExceptionAdviceMessage>> exceptionAdviceMessageGeneratorOptional
    ) {
        return new DiscordMessageSenderAdditionalExceptionHandler(
                discordExceptionAdviceMessageSender(discordExceptionAdviceProperties),
                exceptionAdviceMessageGeneratorOptional.orElse(new DefaultDiscordExceptionAdviceMessageGenerator())
        );
    }
}
