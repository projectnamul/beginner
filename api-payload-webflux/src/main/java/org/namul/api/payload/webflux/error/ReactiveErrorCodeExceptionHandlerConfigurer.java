package org.namul.api.payload.webflux.error;

import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.error.AdditionalExceptionHandler;
import org.namul.api.payload.core.error.ErrorCodeExceptionHandlerConfigurer;
import org.namul.api.payload.core.writer.FailureResponseWriter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.codec.DecodingException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Configurer for handling reactive-specific exceptions in a WebFlux environment.
 * <p>
 * This class extends the base configurer to provide specialized handlers for
 * Non-blocking I/O and Reactive Stream related exceptions.
 *
 * @param <T> A type that extends {@link BaseErrorCode}
 * @author Jeongmo Seo
 */
public class ReactiveErrorCodeExceptionHandlerConfigurer<T extends BaseErrorCode> extends ErrorCodeExceptionHandlerConfigurer<T> {

    /**
     * Constructs a new configurer with the specified reactive failure response writer.
     *
     * @param failureResponseWriter the writer used to render error responses
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer(FailureResponseWriter<T> failureResponseWriter) {
        super(failureResponseWriter);
    }

    /**
     * Configures default handlers for common reactive exceptions.
     * <p>
     * This method maps input-related exceptions (e.g., validation, decoding, type mismatch)
     * to the provided {@code badRequestError} and maps general exceptions to the
     * {@code internalServerError}.
     *
     * @param badRequestError the error code for 400-series client errors (e.g., validation, input)
     * @param internalServerError the error code for 500-series server errors
     * @return this configurer for method chaining
     * @author Jeongmo Seo
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> withDefault(T badRequestError, T internalServerError) {
        return this
                .addWebExchangeBindException(badRequestError)
                .addDecodingException(badRequestError)
                .addMethodNotAllowedException(badRequestError)
                .addServerWebInputException(badRequestError)
                .addTypeMismatchException(badRequestError)
                .addResponseStatusException(badRequestError)
                .addGlobalException(internalServerError);
    }

    /**
     * Adds a default handler for {@link WebExchangeBindException}.
     * Typically occurs during @Valid annotation processing on reactive command objects.
     *
     * @param code the error code to return for validation failures
     * @return this configurer for method chaining
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addWebExchangeBindException(T code) {
        this.addAdvice(WebExchangeBindException.class, code);
        return this;
    }

    /**
     * Adds a default handler for {@link DecodingException}.
     * Occurs when the reactive pipeline fails to decode the request body (e.g., invalid JSON).
     *
     * @param code the error code to return for decoding failures
     * @return this configurer for method chaining
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addDecodingException(T code) {
        this.addAdvice(DecodingException.class, code);
        return this;
    }

    /**
     * Adds a default handler for {@link MethodNotAllowedException}.
     * Occurs when the HTTP method is not supported for the requested endpoint.
     *
     * @param code the error code to return for 405 Method Not Allowed
     * @return this configurer for method chaining
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addMethodNotAllowedException(T code) {
        this.addAdvice(MethodNotAllowedException.class, code);
        return this;
    }

    /**
     * Adds a default handler for {@link ServerWebInputException}.
     * A base exception for any input-related errors, such as missing query parameters or path variables.
     *
     * @param code the error code to return for generic web input failures
     * @return this configurer for method chaining
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addServerWebInputException(T code) {
        this.addAdvice(ServerWebInputException.class, code);
        return this;
    }

    /**
     * Adds a default handler for {@link ResponseStatusException}.
     * Used for handling various HTTP status-related exceptions within the reactive flow.
     *
     * @param code the error code to return for status-based failures
     * @return this configurer for method chaining
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addResponseStatusException(T code) {
        this.addAdvice(ResponseStatusException.class, code);
        return this;
    }

    /**
     * Adds a default handler for {@link TypeMismatchException}.
     * Occurs when request parameter or path variable type conversion fails.
     *
     * @param code the error code to return for type mismatch failures
     * @return this configurer for method chaining
     */
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addTypeMismatchException(T code) {
        this.addAdvice(TypeMismatchException.class, code);
        return this;
    }

    @Override
    public <E extends Throwable> ReactiveErrorCodeExceptionHandlerConfigurer<T> addAdvice(Class<E> cls, T code) {
        super.addAdvice(cls, code);
        return this;
    }

    @Override
    public <E extends Throwable> ReactiveErrorCodeExceptionHandlerConfigurer<T> deleteAdvice(Class<E> cls) {
        super.deleteAdvice(cls);
        return this;
    }

    @Override
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> setAdditionalExceptionHandlersExecutor(Executor executor) {
        super.setAdditionalExceptionHandlersExecutor(executor);
        return this;
    }

    @Override
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandlers(List<AdditionalExceptionHandler<T>> additionalExceptionHandlers) {
        super.addAdditionalExceptionHandlers(additionalExceptionHandlers);
        return this;
    }

    @Override
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addAdditionalExceptionHandler(AdditionalExceptionHandler<T> additionalExceptionHandler) {
        super.addAdditionalExceptionHandler(additionalExceptionHandler);
        return this;
    }

    @Override
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addServerApplication(T code) {
        super.addServerApplication(code);
        return this;
    }

    @Override
    public ReactiveErrorCodeExceptionHandlerConfigurer<T> addGlobalException(T code) {
        super.addGlobalException(code);
        return this;
    }

}
