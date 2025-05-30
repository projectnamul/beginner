package org.namul.api.payload.util;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.code.dto.SuccessReasonDTO;
import org.namul.api.payload.response.DefaultResponse;
import org.namul.api.payload.response.BaseResponse;

public class DefaultResponseWriteUtil implements ResponseWriteUtil {

    @Override
    public <T> BaseResponse writeResponse(SuccessReasonDTO successReasonDTO, T result) {
        return new DefaultResponse<>(true, successReasonDTO.getCode(), successReasonDTO.getMessage(), result);
    }

    @Override
    public <T> BaseResponse writeResponse(ErrorReasonDTO errorReasonDTO, T result) {
        return new DefaultResponse<>(false, errorReasonDTO.getCode(), errorReasonDTO.getMessage(), result);
    }
}
