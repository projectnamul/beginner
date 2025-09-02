package org.namul.api.payload.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;
import org.springframework.beans.TypeMismatchException;

public class TypeMismatchExceptionHandler extends AbstractExceptionAdviceHandler<TypeMismatchException> {

    private static final String MESSAGE_FORMAT = "%s(%s) 요청 파라미터의 형식이 올바르지 않습니다.";

    public TypeMismatchExceptionHandler(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
    }

    @Override
    public Object getMessage(HttpServletRequest request, TypeMismatchException e, ErrorReasonDTO errorReasonDTO) {
        return String.format(MESSAGE_FORMAT, e.getPropertyName(), e.getRequiredType());
    }
}
