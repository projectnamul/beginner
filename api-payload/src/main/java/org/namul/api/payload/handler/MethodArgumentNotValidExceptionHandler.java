package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class MethodArgumentNotValidExceptionHandler<R extends ErrorReasonDTO> implements ExceptionAdviceHandler<MethodArgumentNotValidException, R> {

    private final FailureResponseWriter<R> failureResponseWriter;

    @Override
    public BaseResponse handleException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response, R errorReasonDTO) {
        Object message = getMessage(e, errorReasonDTO);
        return failureResponseWriter.onFailure(errorReasonDTO, message);
    }

    @Override
    public Object getMessage(MethodArgumentNotValidException e, R errorReasonDTO) {
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
