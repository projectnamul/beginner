package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.namul.api.payload.code.BaseErrorCode;

public interface AdditionalExceptionHandler<T extends BaseErrorCode> {
    void doHandle(HttpServletRequest request, HttpServletResponse response, Exception e, T code);
}
