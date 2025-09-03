package org.namul.api.payload.error.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.error.AdditionalExceptionHandler;
import org.namul.api.payload.error.ExceptionAdviceRegistry;
import org.namul.api.payload.message.ExceptionAdviceMessage;
import org.namul.api.payload.message.ExceptionAdviceMessageSender;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;

/**
 * The registry contains sender and generator
 * @param <T> The type of ExceptionAdviceMessage
 */
@RequiredArgsConstructor
public class MessageSenderAdditionalExceptionHandler<T extends ExceptionAdviceMessage> implements AdditionalExceptionHandler {
    private final ExceptionAdviceMessageSender<T> sender;
    private final ExceptionAdviceMessageGenerator<T> generator;

    @Override
    public <E extends Exception> void doHandle(HttpServletRequest request, HttpServletResponse response, E e, ExceptionAdviceRegistry<E> registry) {
        this.sendMessage(request, registry.getCls(), e);
    }

    private <E extends Exception> void sendMessage(HttpServletRequest request, Class<E> cls, Exception e) {
        if (this.sender.isEnable(cls)) {
            this.sender.sendMessage(this.generator.createMessage(request, e));
        }
    }
}
