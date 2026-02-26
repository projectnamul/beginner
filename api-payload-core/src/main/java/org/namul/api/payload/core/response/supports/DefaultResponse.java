package org.namul.api.payload.core.response.supports;

import lombok.Getter;
import lombok.Setter;
import org.namul.api.payload.core.code.supports.DefaultBaseSuccessCode;
import org.namul.api.payload.core.code.supports.DefaultResponseSuccessCode;
import org.namul.api.payload.core.response.AbstractBaseResponse;

/**
 * The classes for Unifying Responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
@Setter
public class DefaultResponse<T> extends AbstractBaseResponse<T> {

    private String code;
    private String message;
    private Boolean isSuccess;

    public DefaultResponse() {
        super(null);
        this.code = null;
        this.message = null;
        this.isSuccess = false;
    }

    public DefaultResponse(Boolean isSuccess, String code, String message, T result) {
        super(result);
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public static <T> DefaultResponse<T> ok(T result) {
        return onSuccess(DefaultResponseSuccessCode.OK, result);
    }

    public static <T> DefaultResponse<T> created(T result) {
        return onSuccess(DefaultResponseSuccessCode.CREATED, result);
    }

    public static <T> DefaultResponse<T> noContent() {
        return onSuccess(DefaultResponseSuccessCode.DELETED, null);
    }

    public static <T> DefaultResponse<T> onSuccess(DefaultBaseSuccessCode code, T result) {
        return new DefaultResponse<>(true, code.getCode(), code.getMessage(), result);
    }
}