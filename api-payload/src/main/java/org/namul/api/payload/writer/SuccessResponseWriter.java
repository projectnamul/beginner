package org.namul.api.payload.writer;

import org.namul.api.payload.code.dto.SuccessReasonDTO;
import org.namul.api.payload.response.BaseResponse;

/**
 * The class that write responses in case of success
 */
public interface SuccessResponseWriter {
    /**
     * The method generate a response in case of success (OK)
     * @param result The response data value
     * @return The unified response
     * @param <T> The type of response data
     */
    <T> BaseResponse ok(T result);

    /**
     * The method generate a response in case of success (CREATED)
     * @param result The response data value
     * @return The unified response
     * @param <T> The type of response data
     */
    <T> BaseResponse created(T result);

    /**
     * The method generate a response in case of success (NO_CONTENT)
     * @return The unified response
     */
    BaseResponse noContent();

    /**
     * The method generate a response in case of success with BaseSuccessCode
     * @param success The data about success
     * @param result The response data value
     * @return The unified response
     * @param <T> The type of response data
     */
    <T> BaseResponse onSuccess(SuccessReasonDTO success, T result);
}
