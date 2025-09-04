package org.namul.api.payload.error;

import org.namul.api.payload.code.DefaultResponseErrorCode;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultExceptionAdviceConfigurer extends ExceptionAdviceConfigurer {

    public DefaultExceptionAdviceConfigurer(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
        super
                .addConstraintViolation(DefaultResponseErrorCode._CONSTRAINT_VIOLATION.getReason())
                .addMethodArgumentNotValid(DefaultResponseErrorCode._METHOD_ARGUMENT_NOT_VALID.getReason())
                .addHttpMessageNotReadable(DefaultResponseErrorCode._HTTP_MESSAGE_NOT_READABLE.getReason())
                .addHttpRequestMethodNotSupported(DefaultResponseErrorCode._HTTP_REQUEST_METHOD_NOT_SUPPORTED.getReason())
                .addMissingPathVariable(DefaultResponseErrorCode._MISSING_REQUEST_VALUE.getReason())
                .addMissingServletRequestParameter(DefaultResponseErrorCode._MISSING_REQUEST_VALUE.getReason())
                .addNoResourceFound(DefaultResponseErrorCode._NO_RESOURCE_FOUND.getReason())
                .addTypeMismatch(DefaultResponseErrorCode._TYPE_MISMATCH.getReason())
                .addServerApplication(DefaultResponseErrorCode._BAD_REQUEST.getReason())
                .addGlobalException(DefaultResponseErrorCode._INTERNAL_SERVER_ERROR.getReason());
    }
}
