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

/**
 * The class how ExceptionAdvice handles exception
 * @param <T> The interface implementing BaseErrorCode which have method to make response
 */
@Getter
@RequiredArgsConstructor
public class ExceptionAdviceConfigurer<T extends BaseErrorCode> {

    private final Map<Class<? extends Exception>, T> errorCodeMap = new HashMap<>();
    private final FailureResponseWriter<T> failureResponseWriter;
    private final List<AdditionalExceptionHandler<T>> additionalExceptionHandlers = new ArrayList<>();

    /**
     * The method for Default configuration it contains MethodArgumentNotValid, HttpMessageNotReadable, HttpRequestMethodNotSupported, MissingPathVariable, MissingServletRequestParameter, NoResourceFound, TypeMismatch, ServerApplication and Exception
     * it set Exception to have internalServerErrorCode and other Exceptions to have badRequestError
     * @param badRequestError The BaseErrorCode that can return ErrorReasonDTO for bad request error
     * @param internalServerError The BaseErrorCode that can return ErrorReasonDTO for internal server error
     */
    public ExceptionAdviceConfigurer<T> withDefault(T badRequestError,
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
    public ExceptionAdviceConfigurer<T> addAdditionalExceptionHandler(AdditionalExceptionHandler<T> additionalExceptionHandler) {
        this.additionalExceptionHandlers.add(additionalExceptionHandler);
        return this;
    }

    /**
     * Add AdditionalExceptionHandler in ExceptionAdvice
     * @param additionalExceptionHandlers The Handlers for additional function
     */
    public ExceptionAdviceConfigurer<T> addAdditionalExceptionHandlers(List<AdditionalExceptionHandler<T>> additionalExceptionHandlers) {
        this.additionalExceptionHandlers.addAll(additionalExceptionHandlers);
        return this;
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MethodArgumentNotValidException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addMethodArgumentNotValid(T code) {
        return this.addAdvice(MethodArgumentNotValidException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpMessageNotReadableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addHttpMessageNotReadable(T code) {
        return this.addAdvice(HttpMessageNotReadableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpRequestMethodNotSupported
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addHttpRequestMethodNotSupported(T code) {
        return this.addAdvice(HttpRequestMethodNotSupportedException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingPathVariableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addMissingPathVariable(T code) {
        return this.addAdvice(MissingPathVariableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingServletRequestParameterException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addMissingServletRequestParameter(T code) {
        return this.addAdvice(MissingServletRequestParameterException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about NoResourceFoundException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addNoResourceFound(T code) {
        return this.addAdvice(NoResourceFoundException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about TypeMismatchException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addTypeMismatch(T code) {
        return this.addAdvice(TypeMismatchException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ServerApplicationException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addServerApplication(T code) {
        return this.addAdvice(ServerApplicationException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about Exception
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<T> addGlobalException(T code) {
        return this.addAdvice(Exception.class, code);
    }

    /**
     * The method that add ErrorReasonDTO and handler about specific Exception
     * @param cls The exception class
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     * @param <E> The exception type
     */
    public <E extends Exception> ExceptionAdviceConfigurer<T> addAdvice(Class<E> cls, T code) {
        this.errorCodeMap.put(cls, code);
        return this;
    }

    /**
     * The method that delete handler which handle cls exception
     * @param cls The exception class is handled by handler
     * @param <E> The exception type
     */
    public <E extends Exception> ExceptionAdviceConfigurer<T> deleteAdvice(Class<E> cls) {
        this.errorCodeMap.remove(cls);
        return this;
    }

    /**
     * Build Exception Advice with configuration settings
     * @return The configured ExceptionAdvice
     */
    public ExceptionAdvice<T> build() {
        return new ExceptionAdvice<>(this);
    }

}
