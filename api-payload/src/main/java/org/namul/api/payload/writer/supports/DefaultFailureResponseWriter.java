package org.namul.api.payload.writer.supports;

import org.namul.api.payload.code.supports.DefaultBaseErrorCode;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.response.supports.DefaultResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultFailureResponseWriter implements FailureResponseWriter<DefaultBaseErrorCode> {

    @Override
    public BaseResponse onFailure(Exception e, DefaultBaseErrorCode error) {
        return new DefaultResponse<>(false, error.getCode(), error.getMessage(), e == null ? null : e.getMessage());
    }
}
