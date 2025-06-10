package org.namul.api.payload.config;

import org.namul.api.payload.message.SlackExceptionAdviceMessageSender;
import org.namul.api.payload.message.SlackExceptionAdviceUtil;
import org.namul.api.payload.properties.SlackExceptionAdviceProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

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
}
