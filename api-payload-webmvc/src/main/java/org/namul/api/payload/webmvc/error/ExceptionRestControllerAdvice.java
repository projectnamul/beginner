package org.namul.api.payload.webmvc.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.error.ErrorCodeExceptionHandler;
import org.namul.api.payload.core.response.BaseResponse;
import org.namul.api.payload.webmvc.web.HttpServletWebRequestWrapper;
import org.namul.api.payload.webmvc.web.HttpServletWebResponseWrapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * The RestController Advice class which uses ErrorCodeExceptionHandler
 * @param <T> The BaseErrorCode which you use
 */
@RestControllerAdvice
public class ExceptionRestControllerAdvice<T extends BaseErrorCode> {

    private final ErrorCodeExceptionHandler<T> errorCodeExceptionHandler;

    /**
     * The Content-type of response. The default value is "application/json"
     */
    private final String contentType;

    public ExceptionRestControllerAdvice(ErrorCodeExceptionHandler<T> errorCodeExceptionHandler) {
        this.errorCodeExceptionHandler = errorCodeExceptionHandler;
        this.contentType = MediaType.APPLICATION_JSON_VALUE;
    }

    public ExceptionRestControllerAdvice(ErrorCodeExceptionHandler<T> errorCodeExceptionHandler, String contentType) {
        this.errorCodeExceptionHandler = errorCodeExceptionHandler;
        this.contentType = contentType;
    }


    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        T code = errorCodeExceptionHandler.getCode(ex);
        response.setStatus(code.getHttpStatus().value());
        response.setContentType(contentType);

        return errorCodeExceptionHandler.handle(
                HttpServletWebRequestWrapper.wrap(request),
                HttpServletWebResponseWrapper.wrap(response),
                ex
        );
    }
}
