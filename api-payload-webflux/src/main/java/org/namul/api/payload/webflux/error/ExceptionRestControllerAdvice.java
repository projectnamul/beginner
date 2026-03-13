package org.namul.api.payload.webflux.error;

import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.error.ErrorCodeExceptionHandler;
import org.namul.api.payload.core.response.BaseResponse;
import org.namul.api.payload.webflux.web.ReactiveWebRequestWrapper;
import org.namul.api.payload.webflux.web.ReactiveWebResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

/**
 * Centralized exception handler for reactive web services using api-payload-core.
 * <p>
 * This advice intercepts all exceptions thrown during the reactive pipeline,
 * converts them into standardized {@link BaseResponse} formats using the
 * provided {@link ErrorCodeExceptionHandler}.
 *
 * @param <T> a type extending {@link BaseErrorCode} for standardized error definitions
 * @author Jeongmo Seo
 */
@RestControllerAdvice
public class ExceptionRestControllerAdvice<T extends BaseErrorCode> {

    private final ErrorCodeExceptionHandler<T> errorCodeExceptionHandler;
    private final String contentType;

    /**
     * Constructs new advice with the default JSON content type.
     *
     * @param errorCodeExceptionHandler the core engine for matching and handling exceptions
     */
    public ExceptionRestControllerAdvice(ErrorCodeExceptionHandler<T> errorCodeExceptionHandler) {
        this.errorCodeExceptionHandler = errorCodeExceptionHandler;
        this.contentType = MediaType.APPLICATION_JSON_VALUE;
    }

    /**
     * Constructs new advice with a custom content type.
     *
     * @param errorCodeExceptionHandler the core engine for matching and handling exceptions
     * @param contentType the media type to be set in the response header
     */
    public ExceptionRestControllerAdvice(ErrorCodeExceptionHandler<T> errorCodeExceptionHandler, String contentType) {
        this.errorCodeExceptionHandler = errorCodeExceptionHandler;
        this.contentType = contentType;
    }

    /**
     * Handles all types of {@link Exception} occurring within the WebFlux context.
     * <p>
     * It wraps the reactive {@code exchange} into technology-agnostic wrappers and
     * delegates the processing to the {@code errorCodeExceptionHandler}.
     *
     * @param exchange the current reactive web exchange
     * @param ex the exception being handled
     * @return a {@link ResponseEntity} containing the standardized {@link BaseResponse}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(ServerWebExchange exchange, Exception ex) {
        T code = errorCodeExceptionHandler.getCode(ex);

        return ResponseEntity
                .status(code.getHttpStatus())
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(errorCodeExceptionHandler.handle(
                        ReactiveWebRequestWrapper.wrap(exchange.getRequest()),
                        ReactiveWebResponseWrapper.wrap(exchange.getResponse()),
                        ex
                ));
    }
}