package org.namul.api.payload.code.dto.supports;

import lombok.Builder;
import lombok.Getter;
import org.namul.api.payload.code.dto.SuccessReasonDTO;
import org.springframework.http.HttpStatus;

/**
 * The default ReasonDTO
 */
@Getter
@Builder
public class DefaultResponseSuccessReasonDTO implements SuccessReasonDTO {
    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;
}
