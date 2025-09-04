package org.namul.api.payload.error.handler.supports;

import org.namul.api.payload.error.handler.MessageSenderAdditionalExceptionHandler;
import org.namul.api.payload.message.DiscordExceptionAdviceMessage;
import org.namul.api.payload.message.ExceptionAdviceMessageSender;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;

public class DiscordMessageSenderAdditionalExceptionHandler extends MessageSenderAdditionalExceptionHandler<DiscordExceptionAdviceMessage> {

    public DiscordMessageSenderAdditionalExceptionHandler(
            ExceptionAdviceMessageSender<DiscordExceptionAdviceMessage> discordExceptionAdviceMessageSender,
            ExceptionAdviceMessageGenerator<DiscordExceptionAdviceMessage> exceptionAdviceMessageGenerator
    ) {
        super(discordExceptionAdviceMessageSender, exceptionAdviceMessageGenerator);
    }

}
