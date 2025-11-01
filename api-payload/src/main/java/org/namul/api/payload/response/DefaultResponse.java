package org.namul.api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.namul.api.payload.code.DefaultResponseSuccessCode;
import org.namul.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO;

/**
 * The classes for Unifying Responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
@Setter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class DefaultResponse<T> extends AbstractBaseResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonProperty("isSuccess")
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
        return onSuccess(DefaultResponseSuccessCode._OK.getReason(), result);
    }

    public static <T> DefaultResponse<T> created(T result) {
        return onSuccess(DefaultResponseSuccessCode._CREATED.getReason(), result);
    }

    public static <T> DefaultResponse<T> noContent() {
        return onSuccess(DefaultResponseSuccessCode._DELETED.getReason(), null);
    }

    public static <T> DefaultResponse<T> onSuccess(DefaultResponseSuccessReasonDTO code, T result) {
        return new DefaultResponse<>(code.isSuccess(), code.getCode(), code.getMessage(), result);
    }
}