package org.namul.api.payload.handler;

import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.MissingPathVariableException;

public class MissingPathVariableExceptionHandler extends AbstractExceptionAdviceHandler<MissingPathVariableException> {

    public MissingPathVariableExceptionHandler(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
    }
}
