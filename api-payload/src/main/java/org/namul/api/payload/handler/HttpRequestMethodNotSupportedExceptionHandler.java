package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Arrays;

public class HttpRequestMethodNotSupportedExceptionHandler<R extends ErrorReasonDTO> extends AbstractExceptionAdviceHandler<HttpRequestMethodNotSupportedException, R> {

    private static final String MESSAGE_FORMAT = "%s 경로는 존재하지만 '%s' 지원하지 않는 메소드입니다. 지원하는 메소드: %s";

    public HttpRequestMethodNotSupportedExceptionHandler(FailureResponseWriter<R> failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, HttpRequestMethodNotSupportedException e, R errorReasonDTO) {
        return String.format(MESSAGE_FORMAT, request.getRequestURI(), e.getMethod(), Arrays.toString(e.getSupportedMethods()));
    }
}
