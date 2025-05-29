package org.namu.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namu.api.payload.code.BaseErrorCode;
import org.namu.api.payload.registry.ExceptionHandlerRegistry;
import org.namu.api.payload.response.BaseResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class MethodArgumentNotValidExceptionAdvice implements ExceptionAdvice<BaseResponse, MethodArgumentNotValidException> {

    private final ExceptionHandlerRegistry exceptionHandlerRegistry;
    private final BaseErrorCode baseErrorCode;

    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handle(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return exceptionHandlerRegistry.getHandler(MethodArgumentNotValidException.class).handleException(e, baseErrorCode.getReason(), errors);
    }
}
