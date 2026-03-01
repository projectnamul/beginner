package org.namul.api.payload.core.error;

import lombok.extern.slf4j.Slf4j;
import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.error.exception.ServerApplicationException;
import org.namul.api.payload.core.response.BaseResponse;
import org.namul.api.payload.core.web.WebRequestWrapper;
import org.namul.api.payload.core.web.WebResponseWrapper;
import org.namul.api.payload.core.writer.FailureResponseWriter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The central engine responsible for intercepting exceptions and generating standardized responses.
 * <p>
 * This class coordinates the error handling flow by mapping caught exceptions to their
 * corresponding {@link BaseErrorCode}, invoking the {@link FailureResponseWriter} for
 * response construction, and triggering any registered {@link AdditionalExceptionHandler}s
 * for asynchronous side effects.
 *
 * @param <T> A type that implements {@link BaseErrorCode}, used to define the error structure.
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
     * Processes an exception and constructs the final API response.
     * <p>
     * <b>Important Consistency Warning:</b> Since {@link AdditionalExceptionHandler}s are
     * executed asynchronously using {@code CompletableFuture.runAsync()}, any mutable
     * information within the {@code WebRequestWrapper} or {@code WebResponseWrapper}
     * must be finalized or safely copied before being passed to this method. Failing to do
     * so may lead to thread-safety issues or data inconsistency if the underlying
     * web server recycles these objects.
     *
     * @param requestWrapper  Metadata of the current incoming request.
     * @param responseWrapper Metadata of the outgoing response.
     * @param t               The thrown exception to be handled.
     * @return A structured {@link BaseResponse} ready for the client.
     */
    public BaseResponse handle(WebRequestWrapper requestWrapper, WebResponseWrapper responseWrapper, Throwable t) {
        T code = getCode(t);
        BaseResponse serverResponse;
        serverResponse = handleDelegated(t, code);

        if (additionalExceptionHandlers != null && !additionalExceptionHandlers.isEmpty()) {
            additionalExceptionHandlers.stream()
                    .filter(item -> item.supports(requestWrapper, responseWrapper, t, code))
                    .forEach(item ->
                        CompletableFuture.runAsync(() -> item.doHandle(requestWrapper, responseWrapper, t, code), executor)
                    );
        }
        return serverResponse;

    }

    /**
     * Resolves the appropriate error code for the given throwable.
     * <p>
     * It prioritized specialized {@link ServerApplicationException} codes,
     * then falls back to the registered exception mapping registry.
     *
     * @param e The intercepted throwable.
     * @return The identified {@link BaseErrorCode} implementation.
     */
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
     * Finds a mapped error code for a specific exception class.
     * <p>
     * This method performs a hierarchical search: if a direct mapping is not found,
     * it traverses up the exception's inheritance tree to find the nearest parent mapping.
     *
     * @param throwableClass The class of the occurring exception.
     * @return The mapped {@link BaseErrorCode}, or {@code null} if no mapping exists.
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
