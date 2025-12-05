package org.namul.api.payload.error.exception;

import org.namul.api.payload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The exception that has failure data as BaseErrorCode
 */
@Getter
@RequiredArgsConstructor
public class ServerApplicationException extends RuntimeException{

    private final BaseErrorCode code;
}
