package org.namul.api.payload.handler;

import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class HttpMessageNotReadableExceptionHandler extends AbstractExceptionAdviceHandler<HttpMessageNotReadableException> {

    public HttpMessageNotReadableExceptionHandler(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
    }
}
