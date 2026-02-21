package org.namul.api.payload.core.code.supports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The default enum containing basic failure data
 */
@Getter
@AllArgsConstructor
public enum DefaultResponseErrorCode implements DefaultBaseErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500_1", "An unexpected server error occurred. Please contact the administrator."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400_1", "The request is invalid. Please check your input."),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "COMMON400_2", "The request body is missing or malformed."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "COMMON405_1", "The requested HTTP method is not supported for this endpoint."),
    NO_RESOURCE_FOUND(HttpStatus.BAD_REQUEST, "COMON400_3", "The requested API endpoint does not exist."),
    MISSING_REQUEST_VALUE(HttpStatus.BAD_REQUEST, "COMMON400_4", "A required request variable is missing or invalid."),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "COMMON400_5", "The type of a request parameter is incorrect."),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "COMMON400_7", "The request data provided is not valid."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401_1", "Authentication is required to access this resource."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403_1", "You do not have permission to access this resource."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404_1", "The requested resource could not be found."),
    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "COMMON400_8", "The resource you are trying to create already exists."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
