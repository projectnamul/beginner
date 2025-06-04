package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

@RequiredArgsConstructor
public class ConstraintViolationExceptionHandler<R extends ErrorReasonDTO> implements ExceptionAdviceHandler<ConstraintViolationException, R> {

    private final FailureResponseWriter<R> failureResponseWriter;

    @Override
    public BaseResponse handleException(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response, R dto) {
        Object errorMessage = getMessage(e, dto);
        return failureResponseWriter.onFailure(dto, errorMessage);
    }

    @Override
    public Object getMessage(ConstraintViolationException e, R errorReasonDTO) {
        return e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("An error occurred while extracting the ConstraintViolationException."));
    }
}
