package org.namul.api.payload.code;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Interface which have base getter to create Error response
 */
public interface BaseErrorCode extends Serializable {
    /**
     * The method to get HttpStatus
     * @return The HttpStatus when success
     */
    HttpStatus getHttpStatus();
}
