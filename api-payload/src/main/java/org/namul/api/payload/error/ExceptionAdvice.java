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
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice<R extends BaseErrorCode> {

    private final FailureResponseWriter<R> failureResponseWriter;
    private final Map<Class<? extends Exception>, R> adviceMap;
    private final List<AdditionalExceptionHandler> additionalExceptionHandlers;

    private final Executor executor = new ThreadPoolExecutor(
            10,
            100,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public ExceptionAdvice(ExceptionAdviceConfigurer exceptionAdviceConfigurer) {
        this.failureResponseWriter = exceptionAdviceConfigurer.getFailureResponseWriter();
        this.adviceMap = exceptionAdviceConfigurer.getAdviceMap();
        this.additionalExceptionHandlers = exceptionAdviceConfigurer.getAdditionalExceptionHandlers();
    }

    /**
     * Handle method when exception occurs
     * @param e The Exception type
     * @return The Response value after handling exception
     */
    @ExceptionHandler
    @SuppressWarnings("unchecked")
    public <E extends Exception> BaseResponse handle(E e, HttpServletRequest request, HttpServletResponse response) {

        R code;
        if (e instanceof ServerApplicationException) {
            try {
                code = (R) ((ServerApplicationException) e).getCode();
            } catch (ClassCastException ex1) {
                code = this.findRegistry(e.getClass());
            }
        }
        else {
            code = this.findRegistry(e.getClass());
        }
        if (code == null) {
            throw new IllegalArgumentException("The appropriate handler was not found.");
        }

        if (additionalExceptionHandlers != null && !additionalExceptionHandlers.isEmpty()) {
            R finalCode = code;
            additionalExceptionHandlers.forEach(item ->
                    CompletableFuture.runAsync(() -> item.doHandle(request, response, e, finalCode), executor)
            );
        }
        return handleDelegated(e, response, code);
    }

    /**
     * Create BaseResponse with handler and errorReasonDTO
     * @param e The exception class
     * @param response The HttpServletResponse
     * @param code The data to process error response logic
     * @return The created response
     * @param <E> The exception type
     */
    private <E extends Exception> BaseResponse handleDelegated(E e,HttpServletResponse response, R code) {
        response.setStatus(code.getHttpStatus().value());
        return failureResponseWriter.onFailure(e, code);
    }


    /**
     * Find registry(handler, error reason) in map repository, First, find exception class in map. if handler about exception class is not found, find handler about super class.
     * Last handler about Exception class is return. if handler about exception class didn't exist, it will return null
     * @param exceptionClass The exception class occurs
     * @return The exception registry contains handler and ErrorReasonDTO
     */
    public R findRegistry(Class<? extends Exception> exceptionClass) {
        Class<?> current = exceptionClass;
        while (current != null && Exception.class.isAssignableFrom(current)) {
            R code= this.adviceMap.get(current);
            if (code != null) {
                return code;
            }
            current = current.getSuperclass();
        }
        return null;
    }

}
