package org.namul.api.payload.core.error;

import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.web.WebRequestWrapper;
import org.namul.api.payload.core.web.WebResponseWrapper;

/**
 * An extension point for executing side effect logic during exception handling.
 * <p>
 * Implementations of this interface run independently of the primary response generation logic.
 * In the {@code ErrorCodeExceptionHandler}, these handlers are executed asynchronously using
 * {@code CompletableFuture.runAsync()} to prevent blocking the client response.
 * <p>
 * <b>Note:</b> Since execution is asynchronous, developers are responsible for managing
 * data consistency and handling any exceptions that may occur within the {@code doHandle} method.
 *
 * @param <T> A type that implements {@link BaseErrorCode}
 */
public interface AdditionalExceptionHandler<T extends BaseErrorCode> {

    /**
     * Determines whether the additional logic should be executed for the given context.
     *
     * @param request  Metadata of the current incoming request (e.g., URI, HTTP method).
     * @param response Metadata of the outgoing response (e.g., Status code).
     * @param t        The intercepted throwable that triggered the exception handling.
     * @param code     The standardized error metadata mapped to the intercepted throwable.
     * @return {@code true} if the handler should execute; {@code false} otherwise.
     * Defaults to {@code true}.
     */
    default boolean supports(WebRequestWrapper request, WebResponseWrapper response, Throwable t, T code) {
        return true;
    }

    /**
     * Executes the additional logic (e.g., logging, external alerts, analytics).
     * <p>
     * This method is triggered only if {@link #supports} returns {@code true}.
     *
     * @param request  Metadata of the current incoming request (e.g., URI, HTTP method).
     * @param response Metadata of the outgoing response (e.g., Status code).
     * @param t        The intercepted throwable that triggered the exception handling.
     * @param code     The standardized error metadata mapped to the intercepted throwable.
     */
    void doHandle(WebRequestWrapper request, WebResponseWrapper response, Throwable t, T code);
}