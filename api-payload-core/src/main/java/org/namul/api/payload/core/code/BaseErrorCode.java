package org.namul.api.payload.core.code;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Interface which have base getter to create Error response
 * It only provides and needs getHttpStatus to gain HttpStatus from several Exceptions
 * You can implement or extend this interface to add data or methods for creating your own response.
 */
public interface BaseErrorCode extends Serializable {
    /**
     * The method to get HttpStatus
     * @return The HttpStatus when success
     */
    HttpStatus getHttpStatus();
}
