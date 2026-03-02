package org.namul.api.payload.core.writer;

import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.response.BaseResponse;

/**
 * A functional interface responsible for constructing failure responses.
 * <p>
 * This interface defines the strategy for transforming an intercepted exception
 * and its associated error metadata into a concrete {@link BaseResponse}.
 * Developers can implement this to support custom response formats derived
 * from their specific {@link BaseErrorCode} implementations.
 *
 * @param <T> A type that implements {@link BaseErrorCode}, providing the
 * necessary metadata for response construction.
 */
public interface FailureResponseWriter<T extends BaseErrorCode> {

    /**
     * Constructs a failure response based on the intercepted exception and error metadata.
     *
     * @param t     The intercepted throwable that caused the failure.
     * @param error The mapped error metadata defining the failure state.
     * @return A structured {@link BaseResponse} ready for the client.
     */
    BaseResponse onFailure(Throwable t, T error);

}