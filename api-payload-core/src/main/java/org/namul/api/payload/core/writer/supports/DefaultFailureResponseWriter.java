package org.namul.api.payload.core.writer.supports;

import org.namul.api.payload.core.code.supports.DefaultBaseErrorCode;
import org.namul.api.payload.core.response.BaseResponse;
import org.namul.api.payload.core.writer.FailureResponseWriter;
import org.namul.api.payload.core.response.supports.DefaultResponse;

public class DefaultFailureResponseWriter implements FailureResponseWriter<DefaultBaseErrorCode> {

    @Override
    public BaseResponse onFailure(Throwable t, DefaultBaseErrorCode error) {
        return new DefaultResponse<>(false, error.getCode(), error.getMessage(), t == null ? null : t.getMessage());
    }
}
