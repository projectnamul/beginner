package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

@RequiredArgsConstructor
public class GlobalExceptionHandler<R extends ErrorReasonDTO> implements ExceptionAdviceHandler<Exception, R> {

    private final FailureResponseWriter<R> failureResponseWriter;

    @Override
    public BaseResponse handleException(Exception e, HttpServletRequest request, HttpServletResponse response, R errorReasonDTO) {
        return failureResponseWriter.onFailure(errorReasonDTO, e.getMessage());
    }
}
