package org.namul.api.payload.error;

import org.namul.api.payload.code.DefaultResponseErrorCode;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultExceptionAdviceConfigurer extends ExceptionAdviceConfigurer {

    public DefaultExceptionAdviceConfigurer(FailureResponseWriter failureResponseWriter) {
        super(failureResponseWriter);
        super
                .addConstraintViolation(DefaultResponseErrorCode._CONSTRAINT_VIOLATION)
                .addMethodArgumentNotValid(DefaultResponseErrorCode._METHOD_ARGUMENT_NOT_VALID)
                .addHttpMessageNotReadable(DefaultResponseErrorCode._HTTP_MESSAGE_NOT_READABLE)
                .addHttpRequestMethodNotSupported(DefaultResponseErrorCode._HTTP_REQUEST_METHOD_NOT_SUPPORTED)
                .addMissingPathVariable(DefaultResponseErrorCode._MISSING_REQUEST_VALUE)
                .addMissingServletRequestParameter(DefaultResponseErrorCode._MISSING_REQUEST_VALUE)
                .addNoResourceFound(DefaultResponseErrorCode._NO_RESOURCE_FOUND)
                .addTypeMismatch(DefaultResponseErrorCode._TYPE_MISMATCH)
                .addServerApplication(DefaultResponseErrorCode._BAD_REQUEST)
                .addGlobalException(DefaultResponseErrorCode._INTERNAL_SERVER_ERROR);
    }
}
