package org.namul.api.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The abstract classes for unifying responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
@Setter
public abstract class AbstractBaseResponse<T> implements BaseResponse {

    @JsonProperty("result")
    private T result;

    protected AbstractBaseResponse(T result) {
        this.result = result;
    }

}
