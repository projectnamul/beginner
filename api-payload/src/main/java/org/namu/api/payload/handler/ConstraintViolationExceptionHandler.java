package org.namu.api.payload.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.namu.api.payload.code.dto.ErrorReasonDTO;
import org.namu.api.payload.response.BaseResponse;
import org.namu.api.payload.util.ResponseWriteUtil;

@RequiredArgsConstructor
public class ConstraintViolationExceptionHandler implements ExceptionAdviceHandler<ConstraintViolationException> {

    private final ResponseWriteUtil responseWriteUtil;

    @Override
    public <T> BaseResponse handleException(ConstraintViolationException e, ErrorReasonDTO errorReasonDTO, T result) {
        return responseWriteUtil.writeResponse(errorReasonDTO, result);
    }
}
