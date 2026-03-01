package org.namul.api.payload.core.writer;

import org.namul.api.payload.core.code.BaseSuccessCode;
import org.namul.api.payload.core.response.BaseResponse;

/**
 * A functional interface responsible for constructing success responses.
 * <p>
 * This interface defines the strategy for transforming a successful business
 * logic result and its associated metadata into a concrete {@link BaseResponse}.
 * Developers can implement this to support custom response formats derived
 * from their specific {@link BaseSuccessCode} implementations.
 *
 * @param <T> A type that implements {@link BaseSuccessCode}, providing the
 * necessary metadata for response construction.
 */
public interface SuccessResponseWriter<T extends BaseSuccessCode> {

    /**
     * Constructs a success response based on the provided result and success metadata.
     *
     * @param <R>    The type of the result data.
     * @param code   The success metadata defining the response state (e.g., HTTP 200 OK).
     * @param result The actual data payload produced by the business logic.
     * @return A structured {@link BaseResponse} ready for the client.
     */
    <R> BaseResponse onSuccess(T code, R result);
}