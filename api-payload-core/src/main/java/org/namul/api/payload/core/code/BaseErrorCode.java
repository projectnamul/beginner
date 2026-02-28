package org.namul.api.payload.core.code;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * A contract interface that defines the data required to build a standardized error response.
 * <p>
 * This interface acts as a bridge between an application's internal exceptions and
 * the final response generation. It provides the necessary metadata, such as the
 * {@link HttpStatus}, to ensure consistent error payloads across the entire system.
 * Implementations can be extended to include custom fields like internal error codes
 * or localized messages.
 */
public interface BaseErrorCode extends Serializable {

    /**
     * Retrieves the HTTP status mapped to the specific error state.
     * * @return the {@link HttpStatus} to be used in the generated response.
     */
    HttpStatus getHttpStatus();
}