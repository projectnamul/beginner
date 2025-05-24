package org.namu.apiPayload.error;

import org.namu.apiPayload.code.BaseErrorCode;
import org.namu.apiPayload.code.dto.ErrorReasonDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The exception that has failure data as BaseErrorCode
 */
@Getter
@RequiredArgsConstructor
public class ServerApplicationException extends RuntimeException{

    private final BaseErrorCode code;
    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }
}
