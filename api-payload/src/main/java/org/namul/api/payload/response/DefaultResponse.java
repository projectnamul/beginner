package org.namul.api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    public DefaultResponse(Boolean isSuccess, String code, String message, T result) {
        super(result);
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }
}