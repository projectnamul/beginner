package org.namul.api.payload.code;

import org.namul.api.payload.code.dto.SuccessReasonDTO;

/**
 * Interface for implementing data on success
 */
public interface BaseSuccessCode {
    /**
     * The method to get SuccessReasonDTO
     * @return The SuccessReasonDTO contains data about success
     */
    SuccessReasonDTO getReason();
}
