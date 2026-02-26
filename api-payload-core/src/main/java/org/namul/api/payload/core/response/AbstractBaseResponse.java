package org.namul.api.payload.core.response;

import lombok.Getter;
import lombok.Setter;

/**
 * The abstract classes for unifying responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
@Setter
public abstract class AbstractBaseResponse<T> implements BaseResponse {

    private T result;

    protected AbstractBaseResponse(T result) {
        this.result = result;
    }

}
