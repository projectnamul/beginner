package org.namu.api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * The abstract classes for unifying responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
public abstract class AbstractBaseResponse<T> implements BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
    @JsonProperty("result")
    private final T result;

    protected AbstractBaseResponse(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
