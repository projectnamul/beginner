package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.servlet.resource.NoResourceFoundException;

public class NoResourceFoundExceptionHandler extends AbstractExceptionAdviceHandler<NoResourceFoundException> {

    private static final String MESSAGE_FORMAT = "%s /%s 에 해당하는 리소스가 존재하지 않습니다.";

    public NoResourceFoundExceptionHandler(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, NoResourceFoundException e, ErrorReasonDTO errorReasonDTO) {
        return String.format(MESSAGE_FORMAT, e.getHttpMethod(), e.getResourcePath());
    }
}
