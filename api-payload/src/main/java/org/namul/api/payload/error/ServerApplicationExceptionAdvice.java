package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.registry.ExceptionHandlerRegistry;
import org.namul.api.payload.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ServerApplicationExceptionAdvice implements ExceptionAdvice<BaseResponse, ServerApplicationException> {

    private final ExceptionHandlerRegistry exceptionHandlerRegistry;

    @Override
    @ExceptionHandler(ServerApplicationException.class)
    public BaseResponse handle(ServerApplicationException e, HttpServletRequest request, HttpServletResponse response) {

        ErrorReasonDTO errorReason = e.getErrorReason();

        return exceptionHandlerRegistry.getHandler(ServerApplicationException.class).handleException(e, errorReason, null);
    }
}
