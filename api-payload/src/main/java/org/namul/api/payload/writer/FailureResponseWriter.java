package org.namul.api.payload.writer;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;

/**
 * The class that write responses in case of failure
 */
public interface FailureResponseWriter {

    /**
     * Make failure response
     * @param error The data about how to fail
     * @param e Exception data
     * @return The generated return value
     */
    BaseResponse onFailure(Exception e, ErrorReasonDTO error);
}
