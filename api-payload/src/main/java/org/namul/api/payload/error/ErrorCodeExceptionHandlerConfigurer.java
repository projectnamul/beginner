package org.namul.api.payload.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.BaseErrorCode;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.concurrent.*;

/**
 * The class how ExceptionAdvice handles exception
 * @param <T> The interface implementing BaseErrorCode which have method to make response
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
     * The method for Default configuration it contains MethodArgumentNotValid, HttpMessageNotReadable, HttpRequestMethodNotSupported, MissingPathVariable, MissingServletRequestParameter, NoResourceFound, TypeMismatch, ServerApplication and Exception
     * it set Exception to have internalServerErrorCode and other Exceptions to have badRequestError
     * @param badRequestError The BaseErrorCode that can return ErrorReasonDTO for bad request error
     * @param internalServerError The BaseErrorCode that can return ErrorReasonDTO for internal server error
     */
    public ErrorCodeExceptionHandlerConfigurer<T> withDefault(T badRequestError,
                                                              T internalServerError) {
        return this
                .addMethodArgumentNotValid(badRequestError)
                .addHttpMessageNotReadable(badRequestError)
                .addHttpRequestMethodNotSupported(badRequestError)
                .addMissingPathVariable(badRequestError)
                .addMissingServletRequestParameter(badRequestError)
                .addNoResourceFound(badRequestError)
                .addTypeMismatch(badRequestError)
                .addServerApplication(badRequestError)
                .addGlobalException(internalServerError);
    }

    /**
     * Add AdditionalExceptionHandler in ExceptionAdvice
     * @param additionalExceptionHandler The Handler for additional function
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandler(AdditionalExceptionHandler<T> additionalExceptionHandler) {
        this.additionalExceptionHandlers.add(additionalExceptionHandler);
        return this;
    }

    /**
     * Add AdditionalExceptionHandler in ExceptionAdvice
     * @param additionalExceptionHandlers The Handlers for additional function
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandlers(List<AdditionalExceptionHandler<T>> additionalExceptionHandlers) {
        this.additionalExceptionHandlers.addAll(additionalExceptionHandlers);
        return this;
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MethodArgumentNotValidException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addMethodArgumentNotValid(T code) {
        return this.addAdvice(MethodArgumentNotValidException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpMessageNotReadableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addHttpMessageNotReadable(T code) {
        return this.addAdvice(HttpMessageNotReadableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpRequestMethodNotSupported
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addHttpRequestMethodNotSupported(T code) {
        return this.addAdvice(HttpRequestMethodNotSupportedException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingPathVariableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addMissingPathVariable(T code) {
        return this.addAdvice(MissingPathVariableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingServletRequestParameterException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addMissingServletRequestParameter(T code) {
        return this.addAdvice(MissingServletRequestParameterException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about NoResourceFoundException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addNoResourceFound(T code) {
        return this.addAdvice(NoResourceFoundException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about TypeMismatchException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addTypeMismatch(T code) {
        return this.addAdvice(TypeMismatchException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ServerApplicationException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addServerApplication(T code) {
        return this.addAdvice(ServerApplicationException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about Exception
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ErrorCodeExceptionHandlerConfigurer<T> addGlobalException(T code) {
        return this.addAdvice(Exception.class, code);
    }

    /**
     * The method that add ErrorReasonDTO and handler about specific Exception
     * @param cls The exception class
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     * @param <E> The exception type
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
     * Sets the executor used to execute AdditionalExceptionHandlers asynchronously. They were already execute asynchronously, but you can customize executor that be used when it is executed asynchronously.
     * @param executor The executor you want to use when CompletableFuture.runAsync is executed
     */
    public ErrorCodeExceptionHandlerConfigurer<T> setAdditionalExceptionHandlersExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    /**
     * Build Exception Advice with configuration settings
     * @return The configured ExceptionAdvice
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
