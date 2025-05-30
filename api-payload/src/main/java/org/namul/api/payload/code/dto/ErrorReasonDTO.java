package org.namul.api.payload.code.dto;

/**
 * The interface that contains information about failures
 */
public interface ErrorReasonDTO {

    default String getCode() {
        return null;
    }

    default String getMessage() {
        return null;
    }
}
