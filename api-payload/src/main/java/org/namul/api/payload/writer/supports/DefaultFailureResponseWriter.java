package org.namul.api.payload.writer.supports;

import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO;
import org.namul.api.payload.response.DefaultResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultFailureResponseWriter implements FailureResponseWriter<DefaultResponseErrorReasonDTO> {

    @Override
    public <T> DefaultResponse<T> onFailure(DefaultResponseErrorReasonDTO error, T result) {
        return new DefaultResponse<>(false, error.getCode(), error.getMessage(), result);
    }
}
