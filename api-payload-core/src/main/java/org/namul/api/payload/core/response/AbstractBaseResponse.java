package org.namul.api.payload.core.response;

import lombok.Getter;
import lombok.Setter;

/**
 * A skeletal implementation of {@link BaseResponse} to provide a unified data structure.
 * <p>
 * This abstract class serves as the base for all concrete response objects,
 * encapsulating the actual data payload (the "result") to be sent to the client.
 * By using a generic type {@code T}, it ensures flexibility in delivering
 * various types of business data while maintaining a consistent response envelope.
 *
 * @param <T> The type of the result data to be included in the response.
 */
@Getter
@Setter
public abstract class AbstractBaseResponse<T> implements BaseResponse {

    /**
     * The actual data payload produced by the business logic.
     */
    private T result;

    /**
     * Constructs a new response with the specified result payload.
     *
     * @param result The business data to be sent to the client.
     */
    protected AbstractBaseResponse(T result) {
        this.result = result;
    }

}