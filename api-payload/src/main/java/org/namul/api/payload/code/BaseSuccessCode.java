package org.namul.api.payload.code;

import org.namul.api.payload.code.dto.SuccessReasonDTO;

import java.io.Serializable;

/**
 * Interface for implementing data on success
 */
public interface BaseSuccessCode extends Serializable {
    /**
     * The method to get SuccessReasonDTO
     * @return The SuccessReasonDTO contains data about success
     */
    SuccessReasonDTO getReason();
}
