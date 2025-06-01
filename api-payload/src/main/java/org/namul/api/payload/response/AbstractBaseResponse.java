package org.namul.api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * The abstract classes for unifying responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
public abstract class AbstractBaseResponse<T> implements BaseResponse {

    @JsonProperty("result")
    private final T result;

    protected AbstractBaseResponse(T result) {
        this.result = result;
    }

}
