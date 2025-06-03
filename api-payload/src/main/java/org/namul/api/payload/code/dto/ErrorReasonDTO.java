package org.namul.api.payload.code.dto;

import org.springframework.http.HttpStatus;

/**
 * The interface that contains information about failures
 */
public interface ErrorReasonDTO {

    HttpStatus getHttpStatus();
}
