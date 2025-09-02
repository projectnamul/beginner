package org.namul.api.payload.error.configurer;

import org.namul.api.payload.code.DefaultResponseErrorCode;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultExceptionAdviceConfigurer extends ExceptionAdviceConfigurer {

    public DefaultExceptionAdviceConfigurer(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
        super.addConstraintViolation(DefaultResponseErrorCode._CONSTRAINT_VIOLATION.getReason());
        super.addMethodArgumentNotValid(DefaultResponseErrorCode._METHOD_ARGUMENT_NOT_VALID.getReason());
        super.addHttpMessageNotReadable(DefaultResponseErrorCode._HTTP_MESSAGE_NOT_READABLE.getReason());
        super.addHttpRequestMethodNotSupported(DefaultResponseErrorCode._HTTP_REQUEST_METHOD_NOT_SUPPORTED.getReason());
        super.addMissingPathVariable(DefaultResponseErrorCode._MISSING_REQUEST_VALUE.getReason());
        super.addMissingServletRequestParameter(DefaultResponseErrorCode._MISSING_REQUEST_VALUE.getReason());
        super.addNoResourceFound(DefaultResponseErrorCode._NO_RESOURCE_FOUND.getReason());
        super.addTypeMismatch(DefaultResponseErrorCode._TYPE_MISMATCH.getReason());
        super.addServerApplication(DefaultResponseErrorCode._BAD_REQUEST.getReason());
        super.addGlobalException(DefaultResponseErrorCode._INTERNAL_SERVER_ERROR.getReason());
    }
}
