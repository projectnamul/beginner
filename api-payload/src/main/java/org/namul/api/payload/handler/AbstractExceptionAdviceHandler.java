package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.writer.FailureResponseWriter;

@RequiredArgsConstructor
public abstract class AbstractExceptionAdviceHandler<E extends Exception> implements ExceptionAdviceHandler<E> {

    private final FailureResponseWriter failureResponseWriter;

    @Override
    public BaseResponse handleException(E e, HttpServletRequest request, HttpServletResponse response, ErrorReasonDTO errorReasonDTO) {
        return failureResponseWriter.onFailure(errorReasonDTO, getMessage(request, e, errorReasonDTO));
    }
}
