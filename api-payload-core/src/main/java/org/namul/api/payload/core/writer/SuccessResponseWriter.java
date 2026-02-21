package org.namul.api.payload.core.writer;

import org.namul.api.payload.core.code.BaseSuccessCode;
import org.namul.api.payload.core.response.BaseResponse;

/**
 * The class that write responses in case of success.
 * if you make interface or class with BaseSuccessCode, you can implement this interface and make class which create response with your own interface
 * @param <T> The interface implementing BaseSuccessCode which have method to make response
 */
public interface SuccessResponseWriter<T extends BaseSuccessCode> {

    <R> BaseResponse onSuccess(T code, R result);
}
