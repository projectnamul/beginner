package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.BaseErrorCode;
import org.namul.api.payload.registry.ExceptionHandlerRegistry;
import org.namul.api.payload.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice implements ExceptionAdvice<BaseResponse, Exception> {

    private final ExceptionHandlerRegistry exceptionHandlerRegistry;
    private final BaseErrorCode baseErrorCode;

    @Override
    @ExceptionHandler(Exception.class)
    public BaseResponse handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        return exceptionHandlerRegistry.getHandler(Exception.class).handleException(e, baseErrorCode.getReason(), e.getMessage());
    }
}
