package org.namul.api.payload.error.handler.supports;

import org.namul.api.payload.error.handler.MessageSenderAdditionalExceptionHandler;
import org.namul.api.payload.message.ExceptionAdviceMessageSender;
import org.namul.api.payload.message.SlackExceptionAdviceMessage;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;

public class SlackMessageSenderAdditionalExceptionHandler extends MessageSenderAdditionalExceptionHandler<SlackExceptionAdviceMessage> {

    public SlackMessageSenderAdditionalExceptionHandler(
            ExceptionAdviceMessageSender<SlackExceptionAdviceMessage> slackExceptionAdviceMessageExceptionAdviceMessageSender,
            ExceptionAdviceMessageGenerator<SlackExceptionAdviceMessage> slackExceptionAdviceMessageExceptionAdviceMessageGenerator
    ) {
        super(slackExceptionAdviceMessageExceptionAdviceMessageSender, slackExceptionAdviceMessageExceptionAdviceMessageGenerator);
    }
}
