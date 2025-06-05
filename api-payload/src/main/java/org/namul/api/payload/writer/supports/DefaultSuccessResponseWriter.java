package org.namul.api.payload.writer.supports;

import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO;
import org.namul.api.payload.code.DefaultResponseSuccessCode;
import org.namul.api.payload.response.DefaultResponse;
import org.namul.api.payload.writer.SuccessResponseWriter;
import org.springframework.stereotype.Component;

/**
 * The default ResponseWriter
 */
@Component
@RequiredArgsConstructor
public class DefaultSuccessResponseWriter implements SuccessResponseWriter<DefaultResponseSuccessReasonDTO> {

    @Override
    public <T> DefaultResponse<T> ok(T result) {
        return this.onSuccess(DefaultResponseSuccessCode._OK.getReason(), result);
    }

    @Override
    public <T> DefaultResponse<T> created(T result) {
        return this.onSuccess(DefaultResponseSuccessCode._CREATED.getReason(), result);
    }

    @Override
    public <T> DefaultResponse<T> noContent() {
        return this.onSuccess(DefaultResponseSuccessCode._DELETED.getReason(), null);
    }

    @Override
    public <T> DefaultResponse<T> onSuccess(DefaultResponseSuccessReasonDTO code, T result) {
        return new DefaultResponse<>(code.isSuccess(), code.getCode(), code.getMessage(), result);
    }
}
