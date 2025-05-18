package io.github.namu.apiPayload.error;

import io.github.namu.apiPayload.code.BaseErrorCode;
import io.github.namu.apiPayload.code.dto.ErrorReasonDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Top-level exception that occurs when an application fails
 */
@Getter
@RequiredArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;
    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
