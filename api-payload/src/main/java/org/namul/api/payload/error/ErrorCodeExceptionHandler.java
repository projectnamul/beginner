package org.namul.api.payload.error;

import lombok.extern.slf4j.Slf4j;
import org.namul.api.payload.code.BaseErrorCode;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.web.WebRequestWrapper;
import org.namul.api.payload.web.WebResponseWrapper;
import org.namul.api.payload.writer.FailureResponseWriter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The class for Error handling when error occurs.
 * @param <T> The interface implementing BaseErrorCode which have method to make response
 */
@Slf4j
public class ErrorCodeExceptionHandler<T extends BaseErrorCode> {

    private final FailureResponseWriter<T> failureResponseWriter;
    private final Map<Class<? extends Throwable>, T> errorCodeMap;
    private final List<AdditionalExceptionHandler<T>> additionalExceptionHandlers;
    private final Executor executor;

    public ErrorCodeExceptionHandler(ErrorCodeExceptionHandlerConfigurer<T> errorCodeExceptionHandlerConfigurer) {
        this.failureResponseWriter = errorCodeExceptionHandlerConfigurer.getFailureResponseWriter();
        this.errorCodeMap = errorCodeExceptionHandlerConfigurer.getErrorCodeMap();
        this.additionalExceptionHandlers = errorCodeExceptionHandlerConfigurer.getAdditionalExceptionHandlers();
        this.executor = errorCodeExceptionHandlerConfigurer.getExecutor();
    }

    /**
     * Handle method when exception occurs
     * @param t The Exception type
     * @return The Response value after handling exception
     */
    public BaseResponse handle(WebRequestWrapper requestWrapper, WebResponseWrapper responseWrapper, Throwable t) {
        T code = getCode(t);
        BaseResponse serverResponse;
        serverResponse = handleDelegated(t, code);

        if (additionalExceptionHandlers != null && !additionalExceptionHandlers.isEmpty()) {
            T finalCode = code;
            additionalExceptionHandlers.stream()
                    .filter(item -> item.supports(requestWrapper, responseWrapper, t, finalCode))
                    .forEach(item ->
                        CompletableFuture.runAsync(() -> item.doHandle(requestWrapper, responseWrapper, t, finalCode), executor)
                    );
        }
        return serverResponse;

    }

    public T getCode(Throwable e) {
        T code;
        if (e instanceof ServerApplicationException) {
            code = handleServerApplicationException((ServerApplicationException) e);
            if (code == null) {
                code = handleGeneralException(e);
            }
        }
        else {
            code = handleGeneralException(e);
        }
        return code;
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
     * @param t The info of exception
     * @return The BaseErrorCode to create response
     */
    private T handleGeneralException(Throwable t) {
        T code = this.findCode(t.getClass());
        if (code == null) {
            throw new IllegalArgumentException("The appropriate handler was not found.");
        }
        return code;
    }

    /**
     * Create BaseResponse with handler and errorReasonDTO
     * @param t The exception class
     * @param code The data to process error response logic
     * @return The created response
     */
    private BaseResponse handleDelegated(Throwable t, T code) {
        return failureResponseWriter.onFailure(t, code);
    }


    /**
     * Find BaseErrorCode in map repository, First, find exception class in map. if handler about exception class is not found, find handler about super class.
     * Last handler about Exception class is return. if handler about exception class didn't exist, it will return null
     * @param throwableClass The exception class occurs
     * @return BaseErrorCode to be used for response generation
     */
    private T findCode(Class<? extends Throwable> throwableClass) {
        Class<?> current = throwableClass;
        while (current != null && Throwable.class.isAssignableFrom(current)) {
            T code= this.errorCodeMap.get(current);
            if (code != null) {
                return code;
            }
            current = current.getSuperclass();
        }
        return null;
    }

}
