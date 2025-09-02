package org.namul.api.payload.config;

import org.namul.api.payload.log.DefaultExceptionAdviceLogger;
import org.namul.api.payload.log.ExceptionAdviceLogger;
import org.namul.api.payload.message.*;
import org.namul.api.payload.message.generator.DefaultDiscordExceptionAdviceMessageGenerator;
import org.namul.api.payload.message.generator.DefaultSlackExceptionAdviceMessageGenerator;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.namul.api.payload.writer.supports.DefaultFailureResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@AutoConfiguration(
        after = {DiscordSenderAutoConfiguration.class, SlackSenderAutoConfiguration.class}
)
public class ExceptionAdviceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FailureResponseWriter.class)
    FailureResponseWriter defaultFailureResponseWriter() {
        return new DefaultFailureResponseWriter();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionAdviceLogger.class)
    DefaultExceptionAdviceLogger defaultExceptionAdviceLogger() {
        return new DefaultExceptionAdviceLogger();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionAdviceMessageManager.class)
    ExceptionAdviceMessageManager exceptionAdviceMessageManager(DiscordExceptionAdviceMessageSender discordExceptionAdviceMessageSender,
                                                                Optional<ExceptionAdviceMessageGenerator<DiscordExceptionAdviceMessage>> discordGeneratorOptional,
                                                                SlackExceptionAdviceMessageSender slackExceptionAdviceMessageSender,
                                                                Optional<ExceptionAdviceMessageGenerator<SlackExceptionAdviceMessage>> slackGeneratorOptional) {
        ExceptionAdviceMessageManager manager = new ExceptionAdviceMessageManager();
        manager.addSender(discordExceptionAdviceMessageSender, discordGeneratorOptional.orElseGet(DefaultDiscordExceptionAdviceMessageGenerator::new));
        manager.addSender(slackExceptionAdviceMessageSender, slackGeneratorOptional.orElseGet(DefaultSlackExceptionAdviceMessageGenerator::new));
        return manager;
    }
}
