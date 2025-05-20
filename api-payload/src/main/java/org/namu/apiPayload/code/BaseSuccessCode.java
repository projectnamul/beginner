package org.namu.apiPayload.code;

import org.namu.apiPayload.code.dto.ReasonDTO;

/**
 * Interface for implementing data on success
 */
public interface BaseSuccessCode {
    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();
}
