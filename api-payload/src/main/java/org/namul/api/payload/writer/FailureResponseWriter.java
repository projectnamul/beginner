package org.namul.api.payload.writer;

import org.namul.api.payload.code.BaseErrorCode;
import org.namul.api.payload.response.BaseResponse;

/**
 * The class that write responses in case of failure.
 * if you make interface or class with BaseErrorCode, you can implement this interface and make class which create response with your own interface
 * @param <T> The interface implementing BaseErrorCode which have method to make response
 */
public interface FailureResponseWriter<T extends BaseErrorCode> {

    /**
     * Make failure response.
     * @param error The data about how to fail
     * @param e Exception data
     * @return The generated return value
     */
    BaseResponse onFailure(Exception e, T error);

}
