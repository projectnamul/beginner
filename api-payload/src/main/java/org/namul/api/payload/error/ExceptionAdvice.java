package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.handler.ExceptionAdviceHandler;
import org.namul.api.payload.response.BaseResponse;
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
public class ExceptionAdvice {

    private final Map<Class<? extends Exception>, ExceptionAdviceRegistry<? extends Exception>> adviceMap;
    private final List<AdditionalExceptionHandler> additionalExceptionHandlers;

    private final Executor executor = new ThreadPoolExecutor(
            10,
            100,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    ExceptionAdvice(ExceptionAdviceConfigurer exceptionAdviceConfigurer) {
        this.adviceMap = exceptionAdviceConfigurer.getAdviceMap();
        this.additionalExceptionHandlers = exceptionAdviceConfigurer.getAdditionalExceptionHandlers();
    }

    /**
     * Handle method when exception occurs
     * @param e The Exception type
     * @return The Response value after handling exception
     */
    @ExceptionHandler
    public <E extends Exception> BaseResponse handle(E e, HttpServletRequest request, HttpServletResponse response) {
        ExceptionAdviceRegistry<E> registry = this.findRegistry(e.getClass());
        if (registry == null) {
            throw new IllegalArgumentException("The appropriate handler was not found.");
        }

        additionalExceptionHandlers.forEach(item ->
                        CompletableFuture.runAsync(() -> item.doHandle(request, response, e, registry), executor)
        );
        return handleDelegated(e, request, response, registry);
    }

    /**
     * Create BaseResponse with handler and errorReasonDTO
     * @param e The exception class
     * @param request The HttpServletRequest
     * @param response The HttpServletResponse
     * @param registry The registry contains handler and ErrorReasonDTO
     * @return The created response
     * @param <E> The exception type
     */
    private <E extends Exception> BaseResponse handleDelegated(E e, HttpServletRequest request, HttpServletResponse response, ExceptionAdviceRegistry<E> registry) {
        ErrorReasonDTO reasonDTO = registry.getErrorReasonDTO();
        ExceptionAdviceHandler<E> handler = registry.getHandler();

        response.setStatus(reasonDTO.getHttpStatus().value());

        return handler.handleException(e, request, response, reasonDTO);
    }


    /**
     * Find registry(handler, error reason) in map repository, First, find exception class in map. if handler about exception class is not found, find handler about super class.
     * Last handler about Exception class is return. if handler about exception class didn't exist, it will return null
     * @param exceptionClass The exception class occurs
     * @return The exception registry contains handler and ErrorReasonDTO
     * @param <E> The exception type
     */
    @SuppressWarnings("unchecked")
    public <E extends Exception> ExceptionAdviceRegistry<E> findRegistry(Class<? extends Exception> exceptionClass) {
        Class<?> current = exceptionClass;
        while (current != null && Exception.class.isAssignableFrom(current)) {
            ExceptionAdviceRegistry<?> registry = this.adviceMap.get(current);
            if (registry != null) {
                return (ExceptionAdviceRegistry<E>) registry;
            }
            current = current.getSuperclass();
        }
        return null;
    }

}
