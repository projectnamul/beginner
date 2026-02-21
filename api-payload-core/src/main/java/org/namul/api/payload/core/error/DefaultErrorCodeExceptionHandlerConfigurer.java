package org.namul.api.payload.core.error;

import org.namul.api.payload.core.code.supports.DefaultBaseErrorCode;
import org.namul.api.payload.core.code.supports.DefaultResponseErrorCode;
import org.namul.api.payload.core.writer.FailureResponseWriter;

public class DefaultErrorCodeExceptionHandlerConfigurer extends ErrorCodeExceptionHandlerConfigurer<DefaultBaseErrorCode> {

    public DefaultErrorCodeExceptionHandlerConfigurer(FailureResponseWriter<DefaultBaseErrorCode> failureResponseWriter) {
        super(failureResponseWriter);
        super
                .addMethodArgumentNotValid(DefaultResponseErrorCode.METHOD_ARGUMENT_NOT_VALID)
                .addHttpMessageNotReadable(DefaultResponseErrorCode.HTTP_MESSAGE_NOT_READABLE)
                .addHttpRequestMethodNotSupported(DefaultResponseErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED)
                .addMissingPathVariable(DefaultResponseErrorCode.MISSING_REQUEST_VALUE)
                .addMissingServletRequestParameter(DefaultResponseErrorCode.MISSING_REQUEST_VALUE)
                .addNoResourceFound(DefaultResponseErrorCode.NO_RESOURCE_FOUND)
                .addTypeMismatch(DefaultResponseErrorCode.TYPE_MISMATCH)
                .addServerApplication(DefaultResponseErrorCode.BAD_REQUEST)
                .addGlobalException(DefaultResponseErrorCode.INTERNAL_SERVER_ERROR);
    }
}
