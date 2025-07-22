package org.namul.api.payload.error.configurer;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.ExceptionAdviceRegistry;
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

import java.util.HashMap;
import java.util.Map;

/**
 * The class how ExceptionAdvice handles exception
 * @param <R> This is ErrorReasonDTO that is data to create unified response
 */
@RequiredArgsConstructor
public class ExceptionAdviceConfigurer<R extends ErrorReasonDTO> {

    private final Map<Class<? extends Exception>, ExceptionAdviceRegistry<? extends Exception,? extends ErrorReasonDTO>> adviceMap = new HashMap<>();
    private final FailureResponseWriter<R> failureResponseWriter;

    /**
     * The method for Default configuration
     * @param badRequestError The error reason DTO for bad request
     * @param internalServerError The error reason DTO for internal server error
     */
    public void withDefault(R badRequestError,
                            R internalServerError) {
        this.addConstraintViolation(badRequestError);
        this.addMethodArgumentNotValid(badRequestError);
        this.addHttpMessageNotReadable(internalServerError);
        this.addHttpRequestMethodNotSupported(badRequestError);
        this.addMissingPathVariable(badRequestError);
        this.addMissingServletRequestParameter(badRequestError);
        this.addNoResourceFound(badRequestError);
        this.addTypeMismatch(badRequestError);
        this.addServerApplication(badRequestError);
        this.addGlobalException(internalServerError);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about ConstraintViolationException
     * @param handler The handler which handle ConstraintViolationException
     * @param r The Reason will be written in response
     */
    public void addConstraintViolation(ExceptionAdviceHandler<ConstraintViolationException, R> handler, R r) {
        this.addAdvice(ConstraintViolationException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MethodArgumentNotValidException
     * @param handler The handler which handle MethodArgumentNotValidException
     * @param r The Reason will be written in response
     */
    public void addMethodArgumentNotValid(ExceptionAdviceHandler<MethodArgumentNotValidException, R> handler, R r) {
        this.addAdvice(MethodArgumentNotValidException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about HttpMessageNotReadableException
     * @param handler The handler which handle HttpMessageNotReadableException
     * @param r The Reason will be written in response
     */
    public void addHttpMessageNotReadable(ExceptionAdviceHandler<HttpMessageNotReadableException, R> handler, R r) {
        this.addAdvice(HttpMessageNotReadableException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about HttpRequestMethodNotSupportedException
     * @param handler The handler which handle HttpRequestMethodNotSupportedException
     * @param r The Reason will be written in response
     */
    public void addHttpRequestMethodNotSupported(ExceptionAdviceHandler<HttpRequestMethodNotSupportedException, R> handler, R r) {
        this.addAdvice(HttpRequestMethodNotSupportedException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MissingPathVariableException
     * @param handler The handler which handle MissingPathVariableException
     * @param r The Reason will be written in response
     */
    public void addMissingPathVariable(ExceptionAdviceHandler<MissingPathVariableException, R> handler, R r) {
        this.addAdvice(MissingPathVariableException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about MissingServletRequestParameterException
     * @param handler The handler which handle MissingServletRequestParameterException
     * @param r The Reason will be written in response
     */
    public void addMissingServletRequestParameter(ExceptionAdviceHandler<MissingServletRequestParameterException, R> handler, R r) {
        this.addAdvice(MissingServletRequestParameterException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about NoResourceFoundException
     * @param handler The handler which handle NoResourceFoundException
     * @param r The Reason will be written in response
     */
    public void addNoResourceFound(ExceptionAdviceHandler<NoResourceFoundException, R> handler, R r) {
        this.addAdvice(NoResourceFoundException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about TypeMismatchException
     * @param handler The handler which handle TypeMismatchException
     * @param r The Reason will be written in response
     */
    public void addTypeMismatch(ExceptionAdviceHandler<TypeMismatchException, R> handler, R r) {
        this.addAdvice(TypeMismatchException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about ServerApplicationException
     * @param handler The handler which handle ServerApplicationException
     * @param r The Reason will be written in response
     */
    public void addServerApplication(ExceptionAdviceHandler<ServerApplicationException, R> handler, R r) {
        this.addAdvice(ServerApplicationException.class, handler, r);
    }

    /**
     * The method that add ExceptionHandler and ErrorReasonDTO about Exception
     * @param handler The handler which handle Exception
     * @param r The Reason will be written in response
     */
    public void addGlobalException(ExceptionAdviceHandler<Exception, R> handler, R r) {
        this.addAdvice(Exception.class, handler, r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ConstraintViolationException
     * @param r The Reason will be written in response
     */
    public void addConstraintViolation(R r) {
        this.addAdvice(ConstraintViolationException.class, new ConstraintViolationExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MethodArgumentNotValidException
     * @param r The Reason will be written in response
     */
    public void addMethodArgumentNotValid(R r) {
        this.addAdvice(MethodArgumentNotValidException.class, new MethodArgumentNotValidExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpMessageNotReadableException
     * @param r The Reason will be written in response
     */
    public void addHttpMessageNotReadable(R r) {
        this.addAdvice(HttpMessageNotReadableException.class, new HttpMessageNotReadableExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpRequestMethodNotSupported
     * @param r The Reason will be written in response
     */
    public void addHttpRequestMethodNotSupported(R r) {
        this.addAdvice(HttpRequestMethodNotSupportedException.class, new HttpRequestMethodNotSupportedExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingPathVariableException
     * @param r The Reason will be written in response
     */
    public void addMissingPathVariable(R r) {
        this.addAdvice(MissingPathVariableException.class, new MissingPathVariableExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingServletRequestParameterException
     * @param r The Reason will be written in response
     */
    public void addMissingServletRequestParameter(R r) {
        this.addAdvice(MissingServletRequestParameterException.class, new MissingServletRequestParameterExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about NoResourceFoundException
     * @param r The Reason will be written in response
     */
    public void addNoResourceFound(R r) {
        this.addAdvice(NoResourceFoundException.class, new NoResourceFoundExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about TypeMismatchException
     * @param r The Reason will be written in response
     */
    public void addTypeMismatch(R r) {
        this.addAdvice(TypeMismatchException.class, new TypeMismatchExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about ServerApplicationException
     * @param r The Reason will be written in response
     */
    public void addServerApplication(R r) {
        this.addAdvice(ServerApplicationException.class, new ServerApplicationExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about Exception
     * @param r The Reason will be written in response
     */
    public void addGlobalException(R r) {
        this.addAdvice(Exception.class, new GlobalExceptionHandler<>(this.failureResponseWriter), r);
    }

    /**
     * The method that add ErrorReasonDTO and handler about specific Exception
     * @param cls The exception class
     * @param handler The handler which handle exception
     * @param r The Reason will be written in response
     * @param <E> The exception type
     */
    public <E extends Exception> void addAdvice(Class<E> cls, ExceptionAdviceHandler<E, R> handler, R r) {
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
     * Find registry(handler, error reason) in map repository, First, find exception class in map. if handler about exception class is not found, find handler about super class.
     * Last handler about Exception class is return. if handler about exception class didn't exist, it will return null
     * @param exceptionClass The exception class occurs
     * @return The exception registry contains handler and ErrorReasonDTO
     * @param <E> The exception type
     */
    @SuppressWarnings("unchecked")
    public <E extends Exception> ExceptionAdviceRegistry<E, R> findRegistry(Class<? extends Exception> exceptionClass) {
        Class<?> current = exceptionClass;
        while (current != null && Exception.class.isAssignableFrom(current)) {
            ExceptionAdviceRegistry<?, ?> registry = this.adviceMap.get(current);
            if (registry != null) {
                return (ExceptionAdviceRegistry<E, R>) registry;
            }
            current = current.getSuperclass();
        }
        return null;
    }
}
