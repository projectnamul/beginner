package org.namul.api.payload.handler;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.ServerApplicationException;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.util.ResponseWriteUtil;

@RequiredArgsConstructor
public class ServerApplicationExceptionHandler implements ExceptionAdviceHandler<ServerApplicationException> {

    private final ResponseWriteUtil responseWriteUtil;

    @Override
    public <T> BaseResponse handleException(ServerApplicationException e, ErrorReasonDTO errorReasonDTO, T result) {
        return responseWriteUtil.writeResponse(errorReasonDTO, result);
    }
}
