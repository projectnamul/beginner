package org.namu.api.payload.code.dto;

/**
 * The interface that contains information about success
 */
public interface SuccessReasonDTO {

    default String getCode() {
        return null;
    }

    default String getMessage() {
        return null;
    }
}
