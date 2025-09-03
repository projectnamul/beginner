package org.namul.api.payload.config;

import org.namul.api.payload.error.handler.supports.SlackMessageSenderAdditionalExceptionHandler;
import org.namul.api.payload.message.SlackExceptionAdviceMessage;
import org.namul.api.payload.message.SlackExceptionAdviceMessageSender;
import org.namul.api.payload.message.SlackExceptionAdviceUtil;
import org.namul.api.payload.message.generator.DefaultSlackExceptionAdviceMessageGenerator;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;
import org.namul.api.payload.properties.SlackExceptionAdviceProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@AutoConfiguration
public class SlackSenderAutoConfiguration {

    @Bean
    SlackExceptionAdviceUtil slackExceptionAdviceUtil() {
        return new SlackExceptionAdviceUtil();
    }

    @Bean
    SlackExceptionAdviceMessageSender slackExceptionAdviceMessageSender(SlackExceptionAdviceProperties slackExceptionAdviceProperties) {
        return new SlackExceptionAdviceMessageSender(slackExceptionAdviceUtil(), slackExceptionAdviceProperties);
    }

    @Bean
    @ConditionalOnMissingBean(SlackMessageSenderAdditionalExceptionHandler.class)
    SlackMessageSenderAdditionalExceptionHandler slackMessageSenderAdditionalExceptionHandler(
            SlackExceptionAdviceProperties slackExceptionAdviceProperties,
            Optional<ExceptionAdviceMessageGenerator<SlackExceptionAdviceMessage>> exceptionAdviceMessageGeneratorOptional
    ) {
        return new SlackMessageSenderAdditionalExceptionHandler(
                slackExceptionAdviceMessageSender(slackExceptionAdviceProperties),
                exceptionAdviceMessageGeneratorOptional.orElse(new DefaultSlackExceptionAdviceMessageGenerator())
        );
    }
}
