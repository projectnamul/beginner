package org.namu.apiPayload.code.dto.supports;

import lombok.Builder;
import lombok.Getter;
import org.namu.apiPayload.code.dto.ErrorReasonDTO;
import org.springframework.http.HttpStatus;

/**
 * The default ErrorReasonDTO
 */
@Getter
@Builder
public class DefaultResponseErrorReasonDTO implements ErrorReasonDTO {
    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;
}
