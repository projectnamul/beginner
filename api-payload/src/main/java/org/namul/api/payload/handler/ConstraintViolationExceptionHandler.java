package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;

public class ConstraintViolationExceptionHandler extends AbstractExceptionAdviceHandler<ConstraintViolationException> {

    public ConstraintViolationExceptionHandler(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, ConstraintViolationException e, ErrorReasonDTO errorReasonDTO) {
        return e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("An error occurred while extracting the ConstraintViolationException."));
    }
}
