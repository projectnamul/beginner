package org.namul.api.payload.error.configurer;

import org.namul.api.payload.code.DefaultResponseErrorCode;
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO;
import org.namul.api.payload.writer.FailureResponseWriter;

public class DefaultExceptionAdviceConfigurer extends ExceptionAdviceConfigurer<DefaultResponseErrorReasonDTO> {

    public DefaultExceptionAdviceConfigurer(FailureResponseWriter<DefaultResponseErrorReasonDTO> failureResponseWriter) {
        super(failureResponseWriter);
        super.withDefault(
                DefaultResponseErrorCode._BAD_REQUEST.getReason(),
                DefaultResponseErrorCode._BAD_REQUEST.getReason(),
                DefaultResponseErrorCode._BAD_REQUEST.getReason(),
                DefaultResponseErrorCode._INTERNAL_SERVER_ERROR.getReason()
        );
    }
}
