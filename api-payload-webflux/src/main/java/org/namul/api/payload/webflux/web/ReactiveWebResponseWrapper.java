package org.namul.api.payload.webflux.web;

import org.namul.api.payload.core.web.WebResponseWrapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;

/**
 * Reactive implementation of {@link WebResponseWrapper} using {@link ServerHttpResponse}.
 * <p>
 * This wrapper provides access to the response metadata within a reactive pipeline,
 * focusing strictly on the outgoing response components.
 * * @author Jeongmo Seo
 */
public class ReactiveWebResponseWrapper implements WebResponseWrapper {

    private final ServerHttpResponse response;

    /**
     * Constructs a new wrapper using the provided {@link ServerHttpResponse}.
     * * @param response the current reactive HTTP response
     */
    public ReactiveWebResponseWrapper(ServerHttpResponse response) {
        this.response = response;
    }

    /**
     * Factory method to create a new {@link ReactiveWebResponseWrapper} instance.
     *
     * @param response the current reactive HTTP response to be wrapped
     * @return a new instance of {@link ReactiveWebResponseWrapper}
     */
    public static ReactiveWebResponseWrapper wrap(ServerHttpResponse response) {
        return new ReactiveWebResponseWrapper(response);
    }

    @Override
    public String getHeader(String name) {
        return response.getHeaders().getFirst(name);
    }

    @Override
    public int getStatus() {
        HttpStatusCode status = response.getStatusCode();
        return (status != null) ? status.value() : -1;
    }

    @Override
    public String getContentType() {
        return String.valueOf(response.getHeaders().getContentType());
    }
}