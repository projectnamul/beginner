package org.namu.api.payload.writer;

import lombok.RequiredArgsConstructor;
import org.namu.api.payload.code.BaseSuccessCode;
import org.namu.api.payload.response.BaseResponse;
import org.namu.api.payload.code.DefaultResponseSuccessCode;
import org.namu.api.payload.util.ResponseWriteUtil;
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
