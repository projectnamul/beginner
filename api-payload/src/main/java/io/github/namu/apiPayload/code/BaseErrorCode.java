package io.github.namu.apiPayload.code;

import io.github.namu.apiPayload.code.dto.ErrorReasonDTO;

/**
 * Interface for implementing data on failures
 */
public interface BaseErrorCode {
    ErrorReasonDTO getReason();

    ErrorReasonDTO getReasonHttpStatus();
}
