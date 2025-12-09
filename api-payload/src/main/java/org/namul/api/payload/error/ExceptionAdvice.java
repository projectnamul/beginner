package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.namul.api.payload.code.BaseErrorCode;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The class for Error handling when error occurs.
 * @param <T> The interface implementing BaseErrorCode which have method to make response
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice<T extends BaseErrorCode> {

    private final FailureResponseWriter<T> failureResponseWriter;
    private final Map<Class<? extends Exception>, T> errorCodeMap;
    private final List<AdditionalExceptionHandler<T>> additionalExceptionHandlers;
    private final Executor executor;

    public ExceptionAdvice(ExceptionAdviceConfigurer<T> exceptionAdviceConfigurer) {
        this.failureResponseWriter = exceptionAdviceConfigurer.getFailureResponseWriter();
        this.errorCodeMap = exceptionAdviceConfigurer.getErrorCodeMap();
        this.additionalExceptionHandlers = exceptionAdviceConfigurer.getAdditionalExceptionHandlers();
        this.executor = exceptionAdviceConfigurer.getExecutor();
    }

    /**
     * Handle method when exception occurs
     * @param e The Exception type
     * @return The Response value after handling exception
     */
    @ExceptionHandler
    public BaseResponse handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        T code;
        BaseResponse serverResponse;
        if (e instanceof ServerApplicationException) {
            code = handleServerApplicationException((ServerApplicationException) e);
            if (code == null) {
                code = handleGeneralException(e);
            }
        }
        else {
            code = handleGeneralException(e);
        }
        serverResponse = handleDelegated(e, response, code);

        if (additionalExceptionHandlers != null && !additionalExceptionHandlers.isEmpty()) {
            T finalCode = code;
            additionalExceptionHandlers.forEach(item ->
                    CompletableFuture.runAsync(() -> item.doHandle(request, response, e, finalCode), executor)
            );
        }
        return serverResponse;

    }

    /**
     * Get BaseErrorCode when only ServerApplicationException occurs
     * @param e The info of ServerApplicationException
     * @return The BaseErrorCode to create response
     */
    @SuppressWarnings("unchecked")
    private T handleServerApplicationException(ServerApplicationException e) {
        T code;
        BaseErrorCode saeBaseErrorCode = e.getCode();
        if (saeBaseErrorCode != null) {
            try {
                code = (T) e.getCode();
                return code;
            } catch (ClassCastException cce) {
                log.warn("ServerApplicationException's internal BaseErrorCode type ({}) is not compatible with ExceptionAdvice's declared R type ({}). Searching registry.",
                        saeBaseErrorCode.getClass().getSimpleName(),
                        "Unknown (due to Type Erasure - R)",
                        cce);
                return null;
            }
        } else {
            log.warn("ServerApplicationException contains null BaseErrorCode. Searching registry.");
            return null;
        }
    }

    /**
     * Get BaseErrorCode when exception occurs
     * @param e The info of exception
     * @return The BaseErrorCode to create response
     */
    private T handleGeneralException(Exception e) {
        T code = this.findCode(e.getClass());
        if (code == null) {
            throw new IllegalArgumentException("The appropriate handler was not found.");
        }
        return code;
    }

    /**
     * Create BaseResponse with handler and errorReasonDTO
     * @param e The exception class
     * @param response The HttpServletResponse
     * @param code The data to process error response logic
     * @return The created response
     */
    private BaseResponse handleDelegated(Exception e,HttpServletResponse response, T code) {
        response.setStatus(code.getHttpStatus().value());
        return failureResponseWriter.onFailure(e, code);
    }


    /**
     * Find BaseErrorCode in map repository, First, find exception class in map. if handler about exception class is not found, find handler about super class.
     * Last handler about Exception class is return. if handler about exception class didn't exist, it will return null
     * @param exceptionClass The exception class occurs
     * @return BaseErrorCode to be used for response generation
     */
    public T findCode(Class<? extends Exception> exceptionClass) {
        Class<?> current = exceptionClass;
        while (current != null && Exception.class.isAssignableFrom(current)) {
            T code= this.errorCodeMap.get(current);
            if (code != null) {
                return code;
            }
            current = current.getSuperclass();
        }
        return null;
    }

}
