package org.namul.api.payload.handler;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RequiredArgsConstructor
public class MethodArgumentNotValidExceptionHandler implements ExceptionAdviceHandler<MethodArgumentNotValidException> {

    private final FailureResponseWriter failureResponseWriter;

    @Override
    public <T> BaseResponse handleException(MethodArgumentNotValidException e, ErrorReasonDTO errorReasonDTO, T result) {
        return failureResponseWriter.onFailure(errorReasonDTO, result);
    }
}
