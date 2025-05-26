package org.namu.api.payload.util;

import org.namu.api.payload.response.DefaultResponse;
import org.namu.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO;
import org.namu.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO;
import org.namu.api.payload.response.BaseResponse;

public class DefaultResponseWriteUtil implements ResponseWriteUtil<DefaultResponseErrorReasonDTO, DefaultResponseSuccessReasonDTO> {

    @Override
    public <T> BaseResponse writeResponse(DefaultResponseSuccessReasonDTO successReasonDTO, T result) {
        return new DefaultResponse<>(successReasonDTO.isSuccess(), successReasonDTO.getCode(), successReasonDTO.getMessage(), result);
    }

    @Override
    public <T> BaseResponse writeResponse(DefaultResponseErrorReasonDTO errorReasonDTO, T result) {
        return new DefaultResponse<>(errorReasonDTO.isSuccess(), errorReasonDTO.getCode(), errorReasonDTO.getMessage(), result);
    }
}
