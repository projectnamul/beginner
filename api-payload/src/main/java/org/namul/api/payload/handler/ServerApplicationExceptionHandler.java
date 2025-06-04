package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

@RequiredArgsConstructor
public class ServerApplicationExceptionHandler<R extends ErrorReasonDTO> implements ExceptionAdviceHandler<ServerApplicationException, R> {

    private final FailureResponseWriter<R> failureResponseWriter;

    @Override
    @SuppressWarnings("unchecked")
    public BaseResponse handleException(ServerApplicationException e, HttpServletRequest request, HttpServletResponse response, R errorReasonDTO) {

        try {
            R errorReason = (R) e.getErrorReason();
            return failureResponseWriter.onFailure(errorReason, null);
        } catch (ClassCastException classCastException) {
            return failureResponseWriter.onFailure(errorReasonDTO, null);
        }
    }

    @Override
    public Object getMessage(ServerApplicationException e, R errorReasonDTO) {
        return e.getCode();
    }
}
