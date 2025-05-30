package org.namu.api.payload.writer;

import org.namu.api.payload.code.BaseSuccessCode;
import org.namu.api.payload.response.BaseResponse;

/**
 * The class that write responses in case of success, Commonly used in RestController
 */
public interface ResponseWriter {
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
     * @param result The response data value
     * @return The unified response
     * @param <T> The type of response data
     */
    <T> BaseResponse noContent(T result);

    /**
     * The method generate a response in case of success with BaseSuccessCode
     * @param code The data about success
     * @param result The response data value
     * @return The unified response
     * @param <T> The type of response data
     */
    <T> BaseResponse onSuccess(BaseSuccessCode code, T result);
}
