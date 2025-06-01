package org.namul.api.payload.handler;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

@RequiredArgsConstructor
public class ServerApplicationExceptionHandler implements ExceptionAdviceHandler<ServerApplicationException> {

    private final FailureResponseWriter failureResponseWriter;

    @Override
    public <T> BaseResponse handleException(ServerApplicationException e, ErrorReasonDTO errorReasonDTO, T result) {
        return failureResponseWriter.onFailure(errorReasonDTO, result);
    }
}
