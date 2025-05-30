package org.namu.api.payload.util;

import org.namu.api.payload.code.dto.ErrorReasonDTO;
import org.namu.api.payload.code.dto.SuccessReasonDTO;
import org.namu.api.payload.response.BaseResponse;

/**
 * The util class generating the same response for failure and success
 */
public interface ResponseWriteUtil {
    /**
     * Create unified response with SuccessReasonDTO and response data value on success
     * @param successReasonDTO The data about success
     * @param result The response data value
     * @return The unified response
     * @param <T> The type of response data
     */
    <T> BaseResponse writeResponse(SuccessReasonDTO successReasonDTO, T result);

    /**
     * Create unified response with ErrorReasonDTO and response data value on failure
     * @param errorReasonDTO The data about failure
     * @param result The response data value
     * @return The unified response
     * @param <T> The type of response data
     */
    <T> BaseResponse writeResponse(ErrorReasonDTO errorReasonDTO, T result);
}
