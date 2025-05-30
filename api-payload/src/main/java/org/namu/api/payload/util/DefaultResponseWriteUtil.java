package org.namu.api.payload.util;

import org.namu.api.payload.code.dto.ErrorReasonDTO;
import org.namu.api.payload.code.dto.SuccessReasonDTO;
import org.namu.api.payload.response.DefaultResponse;
import org.namu.api.payload.response.BaseResponse;

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
