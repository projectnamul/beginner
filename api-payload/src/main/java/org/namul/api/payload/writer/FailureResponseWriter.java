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
     * @param result The additional data for failures to put in return value
     * @return The generated return value
     * @param <T> The generic for result value
     */
    <T> BaseResponse onFailure(ErrorReasonDTO error, T result);
}
