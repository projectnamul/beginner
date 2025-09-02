package org.namul.api.payload.writer.supports;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO;
import org.namul.api.payload.response.DefaultResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultFailureResponseWriter implements FailureResponseWriter {

    @Override
    public <T> DefaultResponse<T> onFailure(ErrorReasonDTO error, T result) {
        DefaultResponseErrorReasonDTO dto = (DefaultResponseErrorReasonDTO) error;
        return new DefaultResponse<>(false, dto.getCode(), dto.getMessage(), result);
    }
}
