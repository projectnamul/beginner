package org.namu.api.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

/**
 * The classes for Unifying Responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class DefaultResponse<T> extends AbstractBaseResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;

    public DefaultResponse(Boolean isSuccess, String code, String message, T result) {
        super(result);
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}