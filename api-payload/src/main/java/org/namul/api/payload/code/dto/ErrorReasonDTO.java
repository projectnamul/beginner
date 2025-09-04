package org.namul.api.payload.code.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * The interface that contains information about failures
 */
public interface ErrorReasonDTO extends Serializable {

    HttpStatus getHttpStatus();
}
