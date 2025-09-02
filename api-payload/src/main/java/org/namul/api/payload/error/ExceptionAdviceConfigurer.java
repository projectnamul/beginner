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
    public void withDefault(ErrorReasonDTO badRequestError,
                            ErrorReasonDTO internalServerError) {
        this.addConstraintViolation(badRequestError);
        this.addMethodArgumentNotValid(badRequestError);
        this.addHttpMessageNotReadable(badRequestError);
        this.addHttpRequestMethodNotSupported(badRequestError);
        this.addMissingPathVariable(badRequestError);
        this.addMissingServletRequestParameter(badRequestError);
        this.addNoResourceFound(badRequestError);
        this.addTypeMismatch(badRequestError);
        this.addServerApplication(badRequestError);
        this.addGlobalException(internalServerError);
    }

    /**
     * Add AdditionalExceptionHandler in ExceptionAdvice
     * @param additionalExceptionHandler The Handler for additional function
     */
    public void addAdditionalExceptionHandler(AdditionalExceptionHandler additionalExceptionHandler) {
        this.additionalExceptionHandlers.add(additionalExceptionHandler);
    }

    /**
     * Add AdditionalExceptionHandler in ExceptionAdvice
     * @param additionalExceptionHandlers The Handlers for additional function
     */
    public void addAdditionalExceptionHandlers(List<AdditionalExceptionHandler> additionalExceptionHandlers) {
        this.additionalExceptionHandlers.addAll(additionalExceptionHandlers);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about ConstraintViolationException
     * @param handler The handler which handle ConstraintViolationException
     * @param r The Reason will be written in response
     */
    public void addConstraintViolation(ExceptionAdviceHandler<ConstraintViolationException> handler, ErrorReasonDTO r) {
        this.addAdvice(ConstraintViolationException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MethodArgumentNotValidException
     * @param handler The handler which handle MethodArgumentNotValidException
     * @param r The Reason will be written in response
     */
    public void addMethodArgumentNotValid(ExceptionAdviceHandler<MethodArgumentNotValidException> handler, ErrorReasonDTO r) {
        this.addAdvice(MethodArgumentNotValidException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about HttpMessageNotReadableException
     * @param handler The handler which handle HttpMessageNotReadableException
     * @param r The Reason will be written in response
     */
    public void addHttpMessageNotReadable(ExceptionAdviceHandler<HttpMessageNotReadableException> handler, ErrorReasonDTO r) {
        this.addAdvice(HttpMessageNotReadableException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about HttpRequestMethodNotSupportedException
     * @param handler The handler which handle HttpRequestMethodNotSupportedException
     * @param r The Reason will be written in response
     */
    public void addHttpRequestMethodNotSupported(ExceptionAdviceHandler<HttpRequestMethodNotSupportedException> handler, ErrorReasonDTO r) {
        this.addAdvice(HttpRequestMethodNotSupportedException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MissingPathVariableException
     * @param handler The handler which handle MissingPathVariableException
     * @param r The Reason will be written in response
     */
    public void addMissingPathVariable(ExceptionAdviceHandler<MissingPathVariableException> handler, ErrorReasonDTO r) {
        this.addAdvice(MissingPathVariableException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MissingServletRequestParameterException
     * @param handler The handler which handle MissingServletRequestParameterException
     * @param r The Reason will be written in response
     */
    public void addMissingServletRequestParameter(ExceptionAdviceHandler<MissingServletRequestParameterException> handler, ErrorReasonDTO r) {
        this.addAdvice(MissingServletRequestParameterException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about NoResourceFoundException
     * @param handler The handler which handle NoResourceFoundException
     * @param r The Reason will be written in response
     */
    public void addNoResourceFound(ExceptionAdviceHandler<NoResourceFoundException> handler, ErrorReasonDTO r) {
        this.addAdvice(NoResourceFoundException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about TypeMismatchException
     * @param handler The handler which handle TypeMismatchException
     * @param r The Reason will be written in response
     */
    public void addTypeMismatch(ExceptionAdviceHandler<TypeMismatchException> handler, ErrorReasonDTO r) {
        this.addAdvice(TypeMismatchException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about ServerApplicationException
     * @param handler The handler which handle ServerApplicationException
     * @param r The Reason will be written in response
     */
    public void addServerApplication(ExceptionAdviceHandler<ServerApplicationException> handler, ErrorReasonDTO r) {
        this.addAdvice(ServerApplicationException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about Exception
     * @param handler The handler which handle Exception
     * @param r The Reason will be written in response
     */
    public void addGlobalException(ExceptionAdviceHandler<Exception> handler, ErrorReasonDTO r) {
        this.addAdvice(Exception.class, handler, r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ConstraintViolationException
     * @param r The Reason will be written in response
     */
    public void addConstraintViolation(ErrorReasonDTO r) {
        this.addAdvice(ConstraintViolationException.class, new ConstraintViolationExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MethodArgumentNotValidException
     * @param r The Reason will be written in response
     */
    public void addMethodArgumentNotValid(ErrorReasonDTO r) {
        this.addAdvice(MethodArgumentNotValidException.class, new MethodArgumentNotValidExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpMessageNotReadableException
     * @param r The Reason will be written in response
     */
    public void addHttpMessageNotReadable(ErrorReasonDTO r) {
        this.addAdvice(HttpMessageNotReadableException.class, new HttpMessageNotReadableExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpRequestMethodNotSupported
     * @param r The Reason will be written in response
     */
    public void addHttpRequestMethodNotSupported(ErrorReasonDTO r) {
        this.addAdvice(HttpRequestMethodNotSupportedException.class, new HttpRequestMethodNotSupportedExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingPathVariableException
     * @param r The Reason will be written in response
     */
    public void addMissingPathVariable(ErrorReasonDTO r) {
        this.addAdvice(MissingPathVariableException.class, new MissingPathVariableExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingServletRequestParameterException
     * @param r The Reason will be written in response
     */
    public void addMissingServletRequestParameter(ErrorReasonDTO r) {
        this.addAdvice(MissingServletRequestParameterException.class, new MissingServletRequestParameterExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about NoResourceFoundException
     * @param r The Reason will be written in response
     */
    public void addNoResourceFound(ErrorReasonDTO r) {
        this.addAdvice(NoResourceFoundException.class, new NoResourceFoundExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about TypeMismatchException
     * @param r The Reason will be written in response
     */
    public void addTypeMismatch(ErrorReasonDTO r) {
        this.addAdvice(TypeMismatchException.class, new TypeMismatchExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ServerApplicationException
     * @param r The Reason will be written in response
     */
    public void addServerApplication(ErrorReasonDTO r) {
        this.addAdvice(ServerApplicationException.class, new ServerApplicationExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about Exception
     * @param r The Reason will be written in response
     */
    public void addGlobalException(ErrorReasonDTO r) {
        this.addAdvice(Exception.class, new GlobalExceptionHandler(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO and handler about specific Exception
     * @param cls The exception class
     * @param handler The handler which handle exception
     * @param r The Reason will be written in response
     * @param <E> The exception type
     */
    public <E extends Exception> void addAdvice(Class<E> cls, ExceptionAdviceHandler<E> handler, ErrorReasonDTO r) {
        this.adviceMap.put(cls, new ExceptionAdviceRegistry<>(cls, handler, r));
    }

    /**
     * The method that delete handler which handle cls exception
     * @param cls The exception class is handled by handler
     * @param <E> The exception type
     */
    public <E extends Exception> void deleteAdvice(Class<E> cls) {
        this.adviceMap.remove(cls);
    }

    /**
     * Build Exception Advice with configuration settings
     * @return The configured ExceptionAdvice
     */
    public ExceptionAdvice build() {
        return new ExceptionAdvice(this);
    }

}
