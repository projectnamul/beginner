package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;

/**
 * The class that handle exceptions to unify responses, Commonly used by ExceptionAdvice
 * @param <E> The exception type which will be handled
 */
public interface ExceptionAdviceHandler<E extends Exception, R extends ErrorReasonDTO> {

    /**
     * The method handle Exception to unify response when it occurs
     * @param e The Exception
     * @return The unified response
     */
    BaseResponse handleException(E e, HttpServletRequest request, HttpServletResponse response, R errorReasonDTO);
}
