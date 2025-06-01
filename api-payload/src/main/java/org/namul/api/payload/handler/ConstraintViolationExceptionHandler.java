package org.namul.api.payload.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

@RequiredArgsConstructor
public class ConstraintViolationExceptionHandler implements ExceptionAdviceHandler<ConstraintViolationException> {

    private final FailureResponseWriter failureResponseWriter;

    @Override
    public <T> BaseResponse handleException(ConstraintViolationException e, ErrorReasonDTO errorReasonDTO, T result) {
        return failureResponseWriter.onFailure(errorReasonDTO, result);
    }
}
