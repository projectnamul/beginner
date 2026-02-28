package org.namul.api.payload.core.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.error.exception.ServerApplicationException;
import org.namul.api.payload.core.writer.FailureResponseWriter;

import java.util.*;
import java.util.concurrent.*;

/**
 * A fluent builder and configurer for constructing an {@link ErrorCodeExceptionHandler}.
 * <p>
 * This class provides a structured way to register exception-to-error-code mappings,
 * add asynchronous side effect handlers, and customize the {@link Executor}
 * responsible for non-blocking task execution (side effect handlers).
 *
 * @param <T> A type that implements {@link BaseErrorCode}, defining the error structure.
 */
@Getter
@RequiredArgsConstructor
public class ErrorCodeExceptionHandlerConfigurer<T extends BaseErrorCode> {

    private final Map<Class<? extends Throwable>, T> errorCodeMap = new HashMap<>();
    private final FailureResponseWriter<T> failureResponseWriter;
    private final List<AdditionalExceptionHandler<T>> additionalExceptionHandlers = new ArrayList<>();
    private Executor executor = null;

    private static final int CORE_POOL_SIZE = 3;
    private static final int MAXIMUM_POOL_SIZE = 15;
    private static final int QUEUE_CAPACITY = 300;
    private static final int KEEP_ALIVE_TIME_SECOND = 60;

    /**
     * Registers a single {@link AdditionalExceptionHandler} to be executed
     * during the exception handling process.
     * <p>
     * These handlers are used to perform non-blocking side effects, such as
     * asynchronous logging or external alerts, without interfering with
     * the primary response generation.
     *
     * @param additionalExceptionHandler The handler to be registered.
     * @return This configurer instance for method chaining.
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandler(AdditionalExceptionHandler<T> additionalExceptionHandler) {
        this.additionalExceptionHandlers.add(additionalExceptionHandler);
        return this;
    }

    /**
     * Registers a list of {@link AdditionalExceptionHandler}s to be executed
     * during the exception handling process.
     * <p>
     * This method allows for bulk registration of multiple side effect handlers.
     *
     * @param additionalExceptionHandlers The list of handlers to be registered.
     * @return This configurer instance for method chaining.
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandlers(List<AdditionalExceptionHandler<T>> additionalExceptionHandlers) {
        this.additionalExceptionHandlers.addAll(additionalExceptionHandlers);
        return this;
    }

    /**
     * Registers a fallback error code for {@link ServerApplicationException}.
     * <p>
     * This code is used when a {@code ServerApplicationException} is thrown without
     * an internal {@link BaseErrorCode} (i.e., the code is {@code null}). It ensures
     * that even if a specific business error code is missing, a standardized
     * response can still be generated.
     *
     * @param code The default {@link BaseErrorCode} to use as a fallback for
     * ServerApplicationExceptions.
     * @return This configurer instance for method chaining.
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addServerApplication(T code) {
        return this.addAdvice(ServerApplicationException.class, code);
    }

    /**
     * Maps the root {@link Exception} class to a global fallback error code.
     *
     * @param code The {@link BaseErrorCode} to use for unhandled general exceptions.
     * @return This configurer instance for method chaining.
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addGlobalException(T code) {
        return this.addAdvice(Exception.class, code);
    }

    /**
     * Registers a mapping between a specific exception type and its corresponding error code.
     *
     * @param cls  The exception class to intercept.
     * @param code The {@link BaseErrorCode} providing metadata for the response.
     * @param <E>  The type of the exception.
     * @return This configurer instance for method chaining.
     */
    public <E extends Throwable> ErrorCodeExceptionHandlerConfigurer<T> addAdvice(Class<E> cls, T code) {
        this.errorCodeMap.put(cls, code);
        return this;
    }

    /**
     * The method that delete handler which handle cls exception
     * @param cls The exception class is handled by handler
     * @param <E> The exception type
     */
    public <E extends Throwable> ErrorCodeExceptionHandlerConfigurer<T> deleteAdvice(Class<E> cls) {
        this.errorCodeMap.remove(cls);
        return this;
    }

    /**
     * Configures a custom {@link Executor} for executing {@link AdditionalExceptionHandler}s.
     * <p>
     * If not explicitly set, a default {@link ThreadPoolExecutor} with pre-defined
     * capacity and a {@code CallerRunsPolicy} will be created during the build process.
     *
     * @param executor The custom executor for asynchronous side effects.
     * @return This configurer instance for method chaining.
     */
    public ErrorCodeExceptionHandlerConfigurer<T> setAdditionalExceptionHandlersExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    /**
     * Finalizes the configuration and creates a new {@link ErrorCodeExceptionHandler}.
     * <p>
     * If no custom executor was provided, this method initializes a default
     * pool with a core size of 3 and a max size of 15.
     *
     * @return A fully configured {@link ErrorCodeExceptionHandler} instance.
     */
    public ErrorCodeExceptionHandler<T> build() {
        if (this.executor == null) {
            this.executor = createDefaultExecutor();
        }
        return new ErrorCodeExceptionHandler<>(this);
    }

    /**
     * Create default executor when you unset custom executor
     */
    private Executor createDefaultExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME_SECOND, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
