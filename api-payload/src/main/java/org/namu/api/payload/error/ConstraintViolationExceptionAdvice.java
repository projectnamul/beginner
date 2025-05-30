package org.namu.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.namu.api.payload.code.BaseErrorCode;
import org.namu.api.payload.registry.ExceptionHandlerRegistry;
import org.namu.api.payload.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ConstraintViolationExceptionAdvice implements ExceptionAdvice<BaseResponse, ConstraintViolationException> {

    private final ExceptionHandlerRegistry exceptionHandlerRegistry;
    private final BaseErrorCode baseErrorCode;

    @Override
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse handle(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("An error occurred while extracting the ConstraintViolationException."));
        return exceptionHandlerRegistry.getHandler(ConstraintViolationException.class).handleException(e, baseErrorCode.getReason(), errorMessage);
    }
}
