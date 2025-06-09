package org.namul.api.payload.message;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.properties.SlackExceptionAdviceProperties;

@RequiredArgsConstructor
public class SlackExceptionAdviceMessageSender implements ExceptionAdviceMessageSender<SlackExceptionAdviceMessage> {

    private final SlackExceptionAdviceUtil slackExceptionAdviceUtil;
    private final SlackExceptionAdviceProperties slackExceptionAdviceProperties;

    @Override
    public void sendMessage(SlackExceptionAdviceMessage slackExceptionAdviceMessage) {
        slackExceptionAdviceUtil.sendAlarm(slackExceptionAdviceProperties.getWebHookUrl(), slackExceptionAdviceMessage);
    }

    @Override
    public <E extends Exception> boolean isEnable(Class<E> cls) {
        return slackExceptionAdviceProperties.isEnable() && slackExceptionAdviceProperties.getScope().contains(cls.getSimpleName());
    }
}
