package org.namul.api.payload.message;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.properties.DiscordExceptionAdviceProperties;

/**
 * The message sender that send message to discord with properties and discordUtil
 */
@RequiredArgsConstructor
public class DiscordExceptionAdviceMessageSender implements ExceptionAdviceMessageSender<DiscordExceptionAdviceMessage> {

    private final DiscordExceptionAdviceUtil discordUtil;
    private final DiscordExceptionAdviceProperties discordExceptionAdviceProperties;

    @Override
    public void sendMessage(DiscordExceptionAdviceMessage discordExceptionAdviceMessage) {
        discordUtil.sendAlarm(discordExceptionAdviceProperties.getWebHookUrl(), discordExceptionAdviceMessage);
    }

    @Override
    public <E extends Exception> boolean isEnable(Class<E> cls) {
        return discordExceptionAdviceProperties.isEnable() && discordExceptionAdviceProperties.getScope().contains(cls.getSimpleName());
    }
}
