package io.github.namu.apiPayload.code.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The class that contains information about success
 */
@Getter
@Builder
public class ReasonDTO {
    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;

    public boolean getIsSuccess(){return this.isSuccess;}
}
