package org.namul.api.payload.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.handler.ExceptionAdviceHandler;

/**
 * The registry contains handler and ErrorReasonDTO
 * @param <E> The type of exception will be handled by handler
 */
@Getter
@RequiredArgsConstructor
public class ExceptionAdviceRegistry<E extends Exception> {

    private final Class<E> cls;
    private final ExceptionAdviceHandler<E> handler;
    private final ErrorReasonDTO errorReasonDTO;
}
