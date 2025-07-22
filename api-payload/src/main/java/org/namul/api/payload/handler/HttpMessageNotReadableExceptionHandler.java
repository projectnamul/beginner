package org.namul.api.payload.handler;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class HttpMessageNotReadableExceptionHandler<R extends ErrorReasonDTO> extends AbstractExceptionAdviceHandler<HttpMessageNotReadableException, R> {

    public HttpMessageNotReadableExceptionHandler(FailureResponseWriter<R> failureResponseWriter) {
        super(failureResponseWriter);
    }
}
