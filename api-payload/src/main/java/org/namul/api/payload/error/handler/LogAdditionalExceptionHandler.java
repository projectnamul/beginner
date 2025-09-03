package org.namul.api.payload.error.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.AdditionalExceptionHandler;
import org.namul.api.payload.error.ExceptionAdviceRegistry;
import org.namul.api.payload.log.ExceptionAdviceLogger;

@Slf4j
public class LogAdditionalExceptionHandler implements ExceptionAdviceLogger, AdditionalExceptionHandler {

    @Override
    public <E extends Exception> void doHandle(HttpServletRequest request, HttpServletResponse response, E e, ExceptionAdviceRegistry<E> registry) {
        ErrorReasonDTO reasonDTO = registry.getErrorReasonDTO();
        log(e, reasonDTO, registry.getHandler().getMessage(request, e, reasonDTO));
    }

    @Override
    public <E extends Exception> void log(E e, ErrorReasonDTO r, Object message) {
        log.warn("Exception Advice({}): {}", e.getClass().getSimpleName(), message, e);
    }
}
