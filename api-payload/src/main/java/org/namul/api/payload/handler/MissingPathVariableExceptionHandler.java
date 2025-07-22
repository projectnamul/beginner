package org.namul.api.payload.handler;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.MissingPathVariableException;

public class MissingPathVariableExceptionHandler<R extends ErrorReasonDTO> extends AbstractExceptionAdviceHandler<MissingPathVariableException, R> {

    public MissingPathVariableExceptionHandler(FailureResponseWriter<R> failureResponseWriter) {
        super(failureResponseWriter);
    }
}
