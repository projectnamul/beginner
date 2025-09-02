package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdditionalExceptionHandler {
    <E extends Exception> void doHandle(HttpServletRequest request, HttpServletResponse response, Exception e, ExceptionAdviceRegistry<E> registry);
}
