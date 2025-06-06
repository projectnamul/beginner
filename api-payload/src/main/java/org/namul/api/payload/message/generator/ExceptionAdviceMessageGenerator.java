package org.namul.api.payload.message.generator;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.message.ExceptionAdviceMessage;

/**
 * The message generator for ExceptionAdviceMessage
 * @param <T> The type of ExceptionAdviceMessage
 */
public interface ExceptionAdviceMessageGenerator<T extends ExceptionAdviceMessage> {

    T createMessage(HttpServletRequest request, Exception exception);
}
