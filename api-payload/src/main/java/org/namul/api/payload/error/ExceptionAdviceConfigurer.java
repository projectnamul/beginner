package org.namul.api.payload.error;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.handler.*;
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
public class ExceptionAdviceConfigurer {

    private final Map<Class<? extends Exception>, ExceptionAdviceRegistry<? extends Exception>> adviceMap = new HashMap<>();
    private final FailureResponseWriter failureResponseWriter;
    private final List<AdditionalExceptionHandler> additionalExceptionHandlers = new ArrayList<>();

    /**
     * The method for Default configuration
     * @param badRequestError The error reason DTO for bad request
     * @param internalServerError The error reason DTO for internal server error
     */
    public ExceptionAdviceConfigurer withDefault(ErrorReasonDTO badRequestError,
                            ErrorReasonDTO internalServerError) {
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
    public ExceptionAdviceConfigurer addAdditionalExceptionHandler(AdditionalExceptionHandler additionalExceptionHandler) {
        this.additionalExceptionHandlers.add(additionalExceptionHandler);
        return this;
    }

    /**
     * Add AdditionalExceptionHandler in ExceptionAdvice
     * @param additionalExceptionHandlers The Handlers for additional function
     */
    public ExceptionAdviceConfigurer addAdditionalExceptionHandlers(List<AdditionalExceptionHandler> additionalExceptionHandlers) {
        this.additionalExceptionHandlers.addAll(additionalExceptionHandlers);
        return this;
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about ConstraintViolationException
     * @param handler The handler which handle ConstraintViolationException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addConstraintViolation(ExceptionAdviceHandler<ConstraintViolationException> handler, ErrorReasonDTO r) {
        return this.addAdvice(ConstraintViolationException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MethodArgumentNotValidException
     * @param handler The handler which handle MethodArgumentNotValidException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addMethodArgumentNotValid(ExceptionAdviceHandler<MethodArgumentNotValidException> handler, ErrorReasonDTO r) {
        return this.addAdvice(MethodArgumentNotValidException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about HttpMessageNotReadableException
     * @param handler The handler which handle HttpMessageNotReadableException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addHttpMessageNotReadable(ExceptionAdviceHandler<HttpMessageNotReadableException> handler, ErrorReasonDTO r) {
        return this.addAdvice(HttpMessageNotReadableException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about HttpRequestMethodNotSupportedException
     * @param handler The handler which handle HttpRequestMethodNotSupportedException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addHttpRequestMethodNotSupported(ExceptionAdviceHandler<HttpRequestMethodNotSupportedException> handler, ErrorReasonDTO r) {
        return this.addAdvice(HttpRequestMethodNotSupportedException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MissingPathVariableException
     * @param handler The handler which handle MissingPathVariableException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addMissingPathVariable(ExceptionAdviceHandler<MissingPathVariableException> handler, ErrorReasonDTO r) {
        return this.addAdvice(MissingPathVariableException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MissingServletRequestParameterException
     * @param handler The handler which handle MissingServletRequestParameterException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addMissingServletRequestParameter(ExceptionAdviceHandler<MissingServletRequestParameterException> handler, ErrorReasonDTO r) {
        return this.addAdvice(MissingServletRequestParameterException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about NoResourceFoundException
     * @param handler The handler which handle NoResourceFoundException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addNoResourceFound(ExceptionAdviceHandler<NoResourceFoundException> handler, ErrorReasonDTO r) {
        return this.addAdvice(NoResourceFoundException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about TypeMismatchException
     * @param handler The handler which handle TypeMismatchException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addTypeMismatch(ExceptionAdviceHandler<TypeMismatchException> handler, ErrorReasonDTO r) {
        return this.addAdvice(TypeMismatchException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about ServerApplicationException
     * @param handler The handler which handle ServerApplicationException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addServerApplication(ExceptionAdviceHandler<ServerApplicationException> handler, ErrorReasonDTO r) {
        return this.addAdvice(ServerApplicationException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about Exception
     * @param handler The handler which handle Exception
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addGlobalException(ExceptionAdviceHandler<Exception> handler, ErrorReasonDTO r) {
        return this.addAdvice(Exception.class, handler, r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ConstraintViolationException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addConstraintViolation(ErrorReasonDTO r) {
        return this.addAdvice(ConstraintViolationException.class, new ConstraintViolationExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MethodArgumentNotValidException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addMethodArgumentNotValid(ErrorReasonDTO r) {
        return this.addAdvice(MethodArgumentNotValidException.class, new MethodArgumentNotValidExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpMessageNotReadableException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addHttpMessageNotReadable(ErrorReasonDTO r) {
        return this.addAdvice(HttpMessageNotReadableException.class, new HttpMessageNotReadableExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpRequestMethodNotSupported
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addHttpRequestMethodNotSupported(ErrorReasonDTO r) {
        return this.addAdvice(HttpRequestMethodNotSupportedException.class, new HttpRequestMethodNotSupportedExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingPathVariableException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addMissingPathVariable(ErrorReasonDTO r) {
        return this.addAdvice(MissingPathVariableException.class, new MissingPathVariableExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingServletRequestParameterException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addMissingServletRequestParameter(ErrorReasonDTO r) {
        return this.addAdvice(MissingServletRequestParameterException.class, new MissingServletRequestParameterExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about NoResourceFoundException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addNoResourceFound(ErrorReasonDTO r) {
        return this.addAdvice(NoResourceFoundException.class, new NoResourceFoundExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about TypeMismatchException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addTypeMismatch(ErrorReasonDTO r) {
        return this.addAdvice(TypeMismatchException.class, new TypeMismatchExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ServerApplicationException
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addServerApplication(ErrorReasonDTO r) {
        return this.addAdvice(ServerApplicationException.class, new ServerApplicationExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about Exception
     * @param r The Reason will be written in response
     */
    public ExceptionAdviceConfigurer addGlobalException(ErrorReasonDTO r) {
        return this.addAdvice(Exception.class, new GlobalExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO and handler about specific Exception
     * @param cls The exception class
     * @param handler The handler which handle exception
     * @param r The Reason will be written in response
     * @param <E> The exception type
     */
    public <E extends Exception> ExceptionAdviceConfigurer addAdvice(Class<E> cls, ExceptionAdviceHandler<E> handler, ErrorReasonDTO r) {
        this.adviceMap.put(cls, new ExceptionAdviceRegistry<>(cls, handler, r));
        return this;
    }

    /**
     * The method that delete handler which handle cls exception
     * @param cls The exception class is handled by handler
     * @param <E> The exception type
     */
    public <E extends Exception> ExceptionAdviceConfigurer deleteAdvice(Class<E> cls) {
        this.adviceMap.remove(cls);
        return this;
    }

    /**
     * Build Exception Advice with configuration settings
     * @return The configured ExceptionAdvice
     */
    public ExceptionAdvice build() {
        return new ExceptionAdvice(this);
    }

}
