package org.namul.api.payload.core.writer.supports;

import org.namul.api.payload.core.code.supports.DefaultBaseSuccessCode;
import org.namul.api.payload.core.response.BaseResponse;
import org.namul.api.payload.core.writer.SuccessResponseWriter;
import org.namul.api.payload.core.response.supports.DefaultResponse;

public class DefaultSuccessResponseWriter implements SuccessResponseWriter<DefaultBaseSuccessCode> {

    @Override
    public <R> BaseResponse onSuccess(DefaultBaseSuccessCode code, R result) {
        return DefaultResponse.onSuccess(code, result);
    }
}
