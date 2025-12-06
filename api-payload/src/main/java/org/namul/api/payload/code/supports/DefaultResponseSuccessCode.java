package org.namul.api.payload.code.supports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The default enum containing basic success data
 */
@Getter
@AllArgsConstructor
public enum DefaultResponseSuccessCode implements DefaultBaseSuccessCode {
    OK(HttpStatus.OK, "COMMON200", "The request was successfully processed."),
    CREATED(HttpStatus.CREATED, "COMMON201", "The resource was successfully created."),
    DELETED(HttpStatus.NO_CONTENT, "COMMON204", "he request was successfully processed, and there is no content to return."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
