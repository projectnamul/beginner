package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MethodArgumentNotValidExceptionHandler extends AbstractExceptionAdviceHandler<MethodArgumentNotValidException> {

    public MethodArgumentNotValidExceptionHandler(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, MethodArgumentNotValidException e, ErrorReasonDTO errorReasonDTO) {
        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return errors;
    }
}
