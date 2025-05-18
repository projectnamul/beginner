package io.github.namu.apiPayload.code;

import io.github.namu.apiPayload.code.dto.ReasonDTO;

/**
 * Interface for implementing data on success
 */
public interface BaseSuccessCode {
    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();
}
