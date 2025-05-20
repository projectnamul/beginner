package org.namu.apiPayload.code;

import org.namu.apiPayload.code.dto.ErrorReasonDTO;

/**
 * Interface for implementing data on failures
 */
public interface BaseErrorCode {
    ErrorReasonDTO getReason();

    ErrorReasonDTO getReasonHttpStatus();
}
