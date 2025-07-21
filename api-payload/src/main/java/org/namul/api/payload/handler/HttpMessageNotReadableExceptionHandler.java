package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.List;

public class HttpMessageNotReadableExceptionHandler<R extends ErrorReasonDTO> extends AbstractExceptionAdviceHandler<HttpMessageNotReadableException, R> {

    private static final String MESSAGE_FORMAT = "요청 본문이 올바르지 않거나 형식이 맞지 않습니다.";

    public HttpMessageNotReadableExceptionHandler(FailureResponseWriter<R> failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, HttpMessageNotReadableException e, R errorReasonDTO) {
        List<String> messages = new ArrayList<>();
        messages.add(MESSAGE_FORMAT);
        messages.add(e.getMessage());
        return messages;
    }
}
