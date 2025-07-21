package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.web.bind.MissingPathVariableException;

public class MissingPathVariableExceptionHandler<R extends ErrorReasonDTO> extends AbstractExceptionAdviceHandler<MissingPathVariableException, R> {

    private static final String MESSAGE_FORMAT = "요청 변수 누락으로 요청 경로가 잘못되었습니다.";

    public MissingPathVariableExceptionHandler(FailureResponseWriter<R> failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, MissingPathVariableException e, R errorReasonDTO) {
        return MESSAGE_FORMAT;
    }
}
