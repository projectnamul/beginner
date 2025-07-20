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

    /**
     * The method for logging message
     *
     * @param request
     * @param e              The exception is occurred
     * @param errorReasonDTO The data about exception
     * @return The error message for logging, it can be String, Map, etc.
     */
    default Object getMessage(HttpServletRequest request, E e, R errorReasonDTO) {
        return e.getMessage();
    }
}
