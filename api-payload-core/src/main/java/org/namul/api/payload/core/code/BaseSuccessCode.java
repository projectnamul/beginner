package org.namul.api.payload.core.code;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * A contract interface that defines the metadata required to build a standardized success response.
 * <p>
 * This interface acts as a bridge between successful business logic execution
 * and the final response construction. It ensures that every successful outcome
 * provides the necessary {@link HttpStatus} and consistent response data.
 */
public interface BaseSuccessCode extends Serializable {

    /**
     * Retrieves the HTTP status mapped to the successful operation.
     * * @return the {@link HttpStatus} to be used in the success response.
     */
    HttpStatus getHttpStatus();
}