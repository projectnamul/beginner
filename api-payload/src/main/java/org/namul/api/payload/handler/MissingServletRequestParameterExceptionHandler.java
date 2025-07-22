package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.MissingServletRequestParameterException;

public class MissingServletRequestParameterExceptionHandler<R extends ErrorReasonDTO> extends AbstractExceptionAdviceHandler<MissingServletRequestParameterException, R> {

    private static final String MESSAGE_FORMAT = "%s(%s) 필수 파라미터가 누락되었습니다.";

    public MissingServletRequestParameterExceptionHandler(FailureResponseWriter<R> failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, MissingServletRequestParameterException e, R errorReasonDTO) {
        return String.format(MESSAGE_FORMAT, e.getParameterName(), e.getParameterType());
    }

}
