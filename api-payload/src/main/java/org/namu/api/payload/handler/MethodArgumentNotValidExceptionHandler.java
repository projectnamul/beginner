package org.namu.api.payload.handler;

import lombok.RequiredArgsConstructor;
import org.namu.api.payload.code.dto.ErrorReasonDTO;
import org.namu.api.payload.response.BaseResponse;
import org.namu.api.payload.util.ResponseWriteUtil;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RequiredArgsConstructor
public class MethodArgumentNotValidExceptionHandler implements ExceptionAdviceHandler<MethodArgumentNotValidException> {

    private final ResponseWriteUtil responseWriteUtil;

    @Override
    public <T> BaseResponse handleException(MethodArgumentNotValidException e, ErrorReasonDTO errorReasonDTO, T result) {
        return responseWriteUtil.writeResponse(errorReasonDTO, result);
    }
}
