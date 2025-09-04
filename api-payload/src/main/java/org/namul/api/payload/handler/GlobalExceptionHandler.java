package org.namul.api.payload.handler;

import org.namul.api.payload.writer.FailureResponseWriter;

public class GlobalExceptionHandler extends AbstractExceptionAdviceHandler<Exception> {

    public GlobalExceptionHandler(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
    }

}
