package org.namu.apiPayload.code;

import org.namu.apiPayload.code.dto.ErrorReasonDTO;

/**
 * Interface for implementing data on failures
 */
public interface BaseErrorCode {
    /**
     * The method to get ErrorReasonDTO
     * @return The ErrorReasonDTO contains data about failure
     */
    ErrorReasonDTO getReason();

}
