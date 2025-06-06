package org.namul.api.payload.message;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.properties.DiscordProperties;

/**
 * The message sender that send message to discord with properties and discordUtil
 */
@RequiredArgsConstructor
public class DiscordExceptionAdviceMessageSender implements ExceptionAdviceMessageSender<DiscordExceptionAdviceMessage> {

    private final DiscordExceptionAdviceUtil discordUtil;
    private final DiscordProperties discordProperties;

    @Override
    public void sendMessage(DiscordExceptionAdviceMessage discordExceptionAdviceMessage) {
        discordUtil.sendAlarm(discordProperties.getWebHookUrl(), discordExceptionAdviceMessage);
    }

    @Override
    public <E extends Exception> boolean isEnable(Class<E> cls) {
        return discordProperties.isEnable() && discordProperties.getScope().contains(cls.getSimpleName());
    }
}
