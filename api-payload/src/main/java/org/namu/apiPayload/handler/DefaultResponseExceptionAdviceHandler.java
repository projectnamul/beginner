package org.namu.apiPayload.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.namu.apiPayload.code.DefaultResponseErrorCode;
import org.namu.apiPayload.code.dto.supports.DefaultResponseErrorReasonDTO;
import org.namu.apiPayload.code.dto.supports.DefaultResponseSuccessReasonDTO;
import org.namu.apiPayload.error.ServerApplicationException;
import org.namu.apiPayload.response.BaseResponse;
import org.namu.apiPayload.util.ResponseWriteUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * The default ExceptionAdviceHandler
 */
@Component
@RequiredArgsConstructor
public class DefaultResponseExceptionAdviceHandler implements ExceptionAdviceHandler<DefaultResponseErrorReasonDTO> {

    private final ResponseWriteUtil<DefaultResponseErrorReasonDTO, DefaultResponseSuccessReasonDTO> responseWriteUtil;

    @Override
    public <T> BaseResponse handleConstraintViolationException(ConstraintViolationException e, T result) {
        return responseWriteUtil.writeResponse(DefaultResponseErrorCode._BAD_REQUEST.getReason(), result);
    }

    @Override
    public <T> BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, T result) {
        return responseWriteUtil.writeResponse(DefaultResponseErrorCode._BAD_REQUEST.getReason(), result);
    }

    @Override
    public BaseResponse handleServerApplicationException(ServerApplicationException e, DefaultResponseErrorReasonDTO errorReasonDTO) {
        return responseWriteUtil.writeResponse(errorReasonDTO, errorReasonDTO.getMessage());
    }

    @Override
    public BaseResponse handleException(Exception e) {
        return responseWriteUtil.writeResponse(DefaultResponseErrorCode._INTERNAL_SERVER_ERROR.getReason(), e.getMessage());
    }
}
