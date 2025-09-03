package org.namul.api.payload.message;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;

/**
 * The registry contains sender and generator
 * @param <T> The type of ExceptionAdviceMessage
 */
@Getter
@RequiredArgsConstructor
public class ExceptionAdviceMessageSenderRegistry<T extends ExceptionAdviceMessage> {
    private final ExceptionAdviceMessageSender<T> sender;
    private final ExceptionAdviceMessageGenerator<T> generator;

    public <E extends Exception> void sendMessage(HttpServletRequest request, Class<E> cls, Exception e) {
        if (this.sender.isEnable(cls)) {
            this.sender.sendMessage(this.generator.createMessage(request, e));
        }
    }
}
