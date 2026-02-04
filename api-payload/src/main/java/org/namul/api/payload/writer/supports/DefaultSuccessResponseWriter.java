package org.namul.api.payload.writer.supports;

import org.namul.api.payload.code.supports.DefaultBaseSuccessCode;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.response.supports.DefaultResponse;
import org.namul.api.payload.writer.SuccessResponseWriter;

public class DefaultSuccessResponseWriter implements SuccessResponseWriter<DefaultBaseSuccessCode> {

    @Override
    public <R> BaseResponse onSuccess(DefaultBaseSuccessCode code, R result) {
        return DefaultResponse.onSuccess(code, result);
    }
}
