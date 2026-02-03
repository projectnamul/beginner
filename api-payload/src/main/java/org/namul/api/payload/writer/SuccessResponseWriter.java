package org.namul.api.payload.writer;


import org.namul.api.payload.code.BaseSuccessCode;
import org.namul.api.payload.response.BaseResponse;

public interface SuccessResponseWriter<T extends BaseSuccessCode> {

    <R> BaseResponse onSuccess(T code, R result);
}
