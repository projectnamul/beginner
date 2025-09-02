package org.namul.api.payload.log;

import lombok.extern.slf4j.Slf4j;
import org.namul.api.payload.code.dto.ErrorReasonDTO;

@Slf4j
public class DefaultExceptionAdviceLogger implements ExceptionAdviceLogger {

    @Override
    public <E extends Exception> void log(E e, ErrorReasonDTO r, Object message) {
        log.warn("Exception Advice({}): {}", e.getClass().getSimpleName(), message, e);
    }
}
