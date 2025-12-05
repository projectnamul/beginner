package org.namul.api.payload.error;

import jakarta.validation.ConstraintViolationException;
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
 */
@Getter
@RequiredArgsConstructor
public class ExceptionAdviceConfigurer<R extends BaseErrorCode> {

    private final Map<Class<? extends Exception>, R> adviceMap = new HashMap<>();
    private final FailureResponseWriter<R> failureResponseWriter;
    private final List<AdditionalExceptionHandler> additionalExceptionHandlers = new ArrayList<>();

    /**
     * The method for Default configuration
     * @param badRequestError The BaseErrorCode that can return ErrorReasonDTO for bad request error
     * @param internalServerError The BaseErrorCode that can return ErrorReasonDTO for internal server error
     */
    public ExceptionAdviceConfigurer<R> withDefault(R badRequestError,
                                                 R internalServerError) {
        return this
                .addConstraintViolation(badRequestError)
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
    public ExceptionAdviceConfigurer<R> addAdditionalExceptionHandler(AdditionalExceptionHandler additionalExceptionHandler) {
        this.additionalExceptionHandlers.add(additionalExceptionHandler);
        return this;
    }

    /**
     * Add AdditionalExceptionHandler in ExceptionAdvice
     * @param additionalExceptionHandlers The Handlers for additional function
     */
    public ExceptionAdviceConfigurer<R> addAdditionalExceptionHandlers(List<AdditionalExceptionHandler> additionalExceptionHandlers) {
        this.additionalExceptionHandlers.addAll(additionalExceptionHandlers);
        return this;
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ConstraintViolationException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addConstraintViolation(R code) {
        return this.addAdvice(ConstraintViolationException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MethodArgumentNotValidException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addMethodArgumentNotValid(R code) {
        return this.addAdvice(MethodArgumentNotValidException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpMessageNotReadableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addHttpMessageNotReadable(R code) {
        return this.addAdvice(HttpMessageNotReadableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpRequestMethodNotSupported
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addHttpRequestMethodNotSupported(R code) {
        return this.addAdvice(HttpRequestMethodNotSupportedException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingPathVariableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addMissingPathVariable(R code) {
        return this.addAdvice(MissingPathVariableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingServletRequestParameterException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addMissingServletRequestParameter(R code) {
        return this.addAdvice(MissingServletRequestParameterException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about NoResourceFoundException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addNoResourceFound(R code) {
        return this.addAdvice(NoResourceFoundException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about TypeMismatchException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addTypeMismatch(R code) {
        return this.addAdvice(TypeMismatchException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ServerApplicationException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addServerApplication(R code) {
        return this.addAdvice(ServerApplicationException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about Exception
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public ExceptionAdviceConfigurer<R> addGlobalException(R code) {
        return this.addAdvice(Exception.class, code);
    }

    /**
     * The method that add ErrorReasonDTO and handler about specific Exception
     * @param cls The exception class
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     * @param <E> The exception type
     */
    public <E extends Exception> ExceptionAdviceConfigurer<R> addAdvice(Class<E> cls, R code) {
        this.adviceMap.put(cls, code);
        return this;
    }

    /**
     * The method that delete handler which handle cls exception
     * @param cls The exception class is handled by handler
     * @param <E> The exception type
     */
    public <E extends Exception> ExceptionAdviceConfigurer<R> deleteAdvice(Class<E> cls) {
        this.adviceMap.remove(cls);
        return this;
    }

    /**
     * Build Exception Advice with configuration settings
     * @return The configured ExceptionAdvice
     */
    public ExceptionAdvice<R> build() {
        return new ExceptionAdvice<>(this);
    }

}
