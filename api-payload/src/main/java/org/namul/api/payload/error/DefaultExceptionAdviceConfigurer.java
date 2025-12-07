package org.namul.api.payload.error;

import org.namul.api.payload.code.supports.DefaultBaseErrorCode;
import org.namul.api.payload.code.supports.DefaultResponseErrorCode;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultExceptionAdviceConfigurer extends ExceptionAdviceConfigurer<DefaultBaseErrorCode> {

    public DefaultExceptionAdviceConfigurer(FailureResponseWriter<DefaultBaseErrorCode> failureResponseWriter) {
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
