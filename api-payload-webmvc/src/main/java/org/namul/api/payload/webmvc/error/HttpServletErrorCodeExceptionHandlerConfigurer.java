package org.namul.api.payload.webmvc.error;

import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.error.AdditionalExceptionHandler;
import org.namul.api.payload.core.error.ErrorCodeExceptionHandlerConfigurer;
import org.namul.api.payload.core.writer.FailureResponseWriter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.concurrent.Executor;

public class HttpServletErrorCodeExceptionHandlerConfigurer<T extends BaseErrorCode> extends ErrorCodeExceptionHandlerConfigurer<T> {

    public HttpServletErrorCodeExceptionHandlerConfigurer(FailureResponseWriter<T> failureResponseWriter) {
        super(failureResponseWriter);
    }

    /**
     * The method for Default configuration it contains MethodArgumentNotValid, HttpMessageNotReadable, HttpRequestMethodNotSupported, MissingPathVariable, MissingServletRequestParameter, NoResourceFound, TypeMismatch, ServerApplication and Exception
     * it set Exception to have internalServerErrorCode and other Exceptions to have badRequestError
     * @param badRequestError The BaseErrorCode that can return ErrorReasonDTO for bad request error
     * @param internalServerError The BaseErrorCode that can return ErrorReasonDTO for internal server error
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> withDefault(T badRequestError,
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
     * The method that add ErrorReasonDTO with default handler about MethodArgumentNotValidException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addMethodArgumentNotValid(T code) {
        return this.addAdvice(MethodArgumentNotValidException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpMessageNotReadableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addHttpMessageNotReadable(T code) {
        return this.addAdvice(HttpMessageNotReadableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about HttpRequestMethodNotSupported
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addHttpRequestMethodNotSupported(T code) {
        return this.addAdvice(HttpRequestMethodNotSupportedException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingPathVariableException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addMissingPathVariable(T code) {
        return this.addAdvice(MissingPathVariableException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about MissingServletRequestParameterException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addMissingServletRequestParameter(T code) {
        return this.addAdvice(MissingServletRequestParameterException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about NoResourceFoundException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addNoResourceFound(T code) {
        return this.addAdvice(NoResourceFoundException.class, code);
    }

    /**
     * The method that add ErrorReasonDTO with default handler about TypeMismatchException
     * @param code The BaseErrorCode which return ErrorReasonDTO that will be written in response
     */
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addTypeMismatch(T code) {
        return this.addAdvice(TypeMismatchException.class, code);
    }

    @Override
    public <E extends Throwable> HttpServletErrorCodeExceptionHandlerConfigurer<T> addAdvice(Class<E> cls, T code) {
        super.addAdvice(cls, code);
        return this;
    }

    @Override
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandler(AdditionalExceptionHandler<T> additionalExceptionHandler) {
        super.addAdditionalExceptionHandler(additionalExceptionHandler);
        return this;
    }

    @Override
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandlers(List<AdditionalExceptionHandler<T>> additionalExceptionHandlers) {
        super.addAdditionalExceptionHandlers(additionalExceptionHandlers);
        return this;
    }

    @Override
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addGlobalException(T code) {
        super.addGlobalException(code);
        return this;
    }

    @Override
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> addServerApplication(T code) {
        super.addServerApplication(code);
        return this;
    }

    @Override
    public <E extends Throwable> HttpServletErrorCodeExceptionHandlerConfigurer<T> deleteAdvice(Class<E> cls) {
        super.deleteAdvice(cls);
        return this;
    }

    @Override
    public HttpServletErrorCodeExceptionHandlerConfigurer<T> setAdditionalExceptionHandlersExecutor(Executor executor) {
        super.setAdditionalExceptionHandlersExecutor(executor);
        return this;
    }
}
