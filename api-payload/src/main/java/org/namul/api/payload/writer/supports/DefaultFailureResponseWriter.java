package org.namul.api.payload.writer.supports;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.response.DefaultResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultFailureResponseWriter implements FailureResponseWriter {

    @Override
    public BaseResponse onFailure(Exception e, ErrorReasonDTO error) {
        DefaultResponseErrorReasonDTO dto = (DefaultResponseErrorReasonDTO) error;
        return new DefaultResponse<>(false, dto.getCode(), dto.getMessage(), e.getMessage());
    }
}
