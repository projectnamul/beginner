package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.namul.api.payload.code.BaseErrorCode;

/**
 * The additional handler add sub logic when ExceptionAdvice handle exception. This logic doesn't affect to creating response logic. So, handlers will be executed with CompletableFuture.runAsync() in ExceptionAdvice.
 * So, you must control consistency problem and Exception that occur in doHandle method, if you need.
 * @param <T> The class or interface implementing BaseErrorCode
 */
public interface AdditionalExceptionHandler<T extends BaseErrorCode> {
    void doHandle(HttpServletRequest request, HttpServletResponse response, Exception e, T code);
}
