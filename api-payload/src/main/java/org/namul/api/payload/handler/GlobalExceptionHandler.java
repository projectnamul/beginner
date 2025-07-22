package org.namul.api.payload.handler;

import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;

public class GlobalExceptionHandler<R extends ErrorReasonDTO> extends AbstractExceptionAdviceHandler<Exception, R> {

    public GlobalExceptionHandler(FailureResponseWriter<R> failureResponseWriter) {
        super(failureResponseWriter);
    }

}
