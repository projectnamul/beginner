package org.namul.api.payload.core.error;

import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.web.WebRequestWrapper;
import org.namul.api.payload.core.web.WebResponseWrapper;

/**
 * The additional handler add sub logic when ExceptionAdvice handle exception. This logic doesn't affect to creating response logic. So, handlers will be executed with CompletableFuture.runAsync() in ExceptionAdvice.
 * So, you must control consistency problem and Exception that occur in doHandle method, if you need.
 * @param <T> The class or interface implementing BaseErrorCode
 */
public interface AdditionalExceptionHandler<T extends BaseErrorCode> {

    /**
     * Method that determine whether to execute additional methods
     * @param t The throwable data when logic that creates error response run
     * @param code The BaseErrorCode when logic that creates error response run
     * @return True or False, If it returns true, additional logic will be executed. If it returns false, additional logic won't be executed.
     */
    default boolean supports(WebRequestWrapper request, WebResponseWrapper response, Throwable t, T code) {
        return true;
    }

    /**
     * Additional logic executed in ExceptionAdvice when an error occurs
     * @param t The throwable data when logic that creates error response run
     * @param code The BaseErrorCode when logic that creates error response run
     */
    void doHandle(WebRequestWrapper request, WebResponseWrapper response, Throwable t, T code);
}
