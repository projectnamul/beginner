package org.namul.api.payload.error.exception;

import org.namul.api.payload.code.BaseErrorCode;
import lombok.Getter;

/**
 * The exception that has failure data as BaseErrorCode
 */
@Getter
public class ServerApplicationException extends RuntimeException {

    private final BaseErrorCode code;

    public ServerApplicationException(BaseErrorCode code) {
        this.code = code;
    }

    public ServerApplicationException(BaseErrorCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }
    public ServerApplicationException(BaseErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
