package org.namul.api.payload.code.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * The interface that contains information about success
 */
public interface SuccessReasonDTO extends Serializable {

    HttpStatus getHttpStatus();
}
