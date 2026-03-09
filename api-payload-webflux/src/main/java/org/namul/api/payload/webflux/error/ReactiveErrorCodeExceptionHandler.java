package org.namul.api.payload.webflux.error;

import org.namul.api.payload.core.code.BaseErrorCode;
import org.namul.api.payload.core.error.ErrorCodeExceptionHandler;
import org.namul.api.payload.core.response.BaseResponse;
import org.namul.api.payload.core.web.WebRequestWrapper;
import org.namul.api.payload.core.web.WebResponseWrapper;
import reactor.core.publisher.Mono;

/**
 * Reactive specialized exception handler that wraps the standard {@link BaseResponse} into a {@link Mono}.
 * <p>
 * This ensures that the exception handling result can be seamlessly integrated into
 * the WebFlux reactive pipeline.
 *
 * @param <T> a type extending {@link BaseErrorCode}
 * @author Jeongmo Seo
 */
public class ReactiveErrorCodeExceptionHandler<T extends BaseErrorCode> extends ErrorCodeExceptionHandler<T> {

    /**
     * Constructs a new reactive exception handler using the specialized reactive configurer.
     *
     * @param configurer the reactive-specific configuration containing exception mappings
     */
    public ReactiveErrorCodeExceptionHandler(ReactiveErrorCodeExceptionHandlerConfigurer<T> configurer) {
        super(configurer);
    }

    /**
     * Handles the exception and wraps the resulting {@link BaseResponse} in a {@link Mono}.
     * @param request  the wrapped reactive request
     * @param response the wrapped reactive response
     * @param ex       the exception to handle
     * @return a {@link Mono} emitting the standardized error response
     */
    public Mono<BaseResponse> handleToMono(WebRequestWrapper request, WebResponseWrapper response, Throwable ex) {
        return Mono.fromSupplier(() -> super.handle(request, response, ex));
    }
}