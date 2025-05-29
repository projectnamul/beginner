package org.namu.api.payload.handler;

import lombok.RequiredArgsConstructor;
import org.namu.api.payload.code.dto.ErrorReasonDTO;
import org.namu.api.payload.response.BaseResponse;
import org.namu.api.payload.util.ResponseWriteUtil;

@RequiredArgsConstructor
public class GlobalExceptionHandler implements ExceptionAdviceHandler<Exception> {

    private final ResponseWriteUtil responseWriteUtil;

    @Override
    public <T> BaseResponse handleException(Exception e, ErrorReasonDTO errorReasonDTO, T result) {
        return responseWriteUtil.writeResponse(errorReasonDTO, result);
    }
}
