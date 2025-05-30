package org.namu.api.payload.error;

import org.namu.api.payload.code.BaseErrorCode;
import org.namu.api.payload.code.dto.ErrorReasonDTO;
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
