package org.namul.api.payload.writer;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.BaseSuccessCode;
import org.namul.api.payload.response.BaseResponse;
import org.namul.api.payload.code.DefaultResponseSuccessCode;
import org.namul.api.payload.util.ResponseWriteUtil;
import org.springframework.stereotype.Component;

/**
 * The default ResponseWriter
 */
@Component
@RequiredArgsConstructor
public class DefaultResponseWriter implements ResponseWriter {

    private final ResponseWriteUtil defaultResponseWriteUtil;

    @Override
    public <T> BaseResponse ok(T result) {
        return this.onSuccess(DefaultResponseSuccessCode._OK, result);
    }

    @Override
    public <T> BaseResponse created(T result) {
        return this.onSuccess(DefaultResponseSuccessCode._CREATED, result);
    }

    @Override
    public <T> BaseResponse noContent(T result) {
        return this.onSuccess(DefaultResponseSuccessCode._DELETED, result);
    }

    @Override
    public <T> BaseResponse onSuccess(BaseSuccessCode code, T result) {
        return defaultResponseWriteUtil.writeResponse(code.getReason(), result);
    }
}
