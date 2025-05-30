package org.namul.api.payload.handler;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;

/**
 * The class that handle exceptions to unify responses, Commonly used by ExceptionAdvice
 * @param <E> The exception type which will be handled
 */
public interface ExceptionAdviceHandler<E extends Exception> {

    /**
     * The method handle Exception to unify response when it occurs
     * @param e The Exception
     * @return The unified response
     */
    <T> BaseResponse handleException(E e, ErrorReasonDTO errorReasonDTO, T result);
}
