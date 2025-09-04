package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.exception.ServerApplicationException;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

@RequiredArgsConstructor
public class ServerApplicationExceptionHandler implements ExceptionAdviceHandler<ServerApplicationException> {

    private final FailureResponseWriter failureResponseWriter;

    @Override
    @SuppressWarnings("unchecked")
    public BaseResponse handleException(ServerApplicationException e, HttpServletRequest request, HttpServletResponse response, ErrorReasonDTO errorReasonDTO) {

        if (e.getErrorReason() != null) {
            return failureResponseWriter.onFailure(e.getErrorReason(), null);
        } else {
            return failureResponseWriter.onFailure(errorReasonDTO, null);
        }
    }

    @Override
    public Object getMessage(HttpServletRequest request, ServerApplicationException e, ErrorReasonDTO errorReasonDTO) {
        return e.getCode();
    }
}
