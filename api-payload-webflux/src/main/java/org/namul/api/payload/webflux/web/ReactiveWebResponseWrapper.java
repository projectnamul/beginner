package org.namul.api.payload.webflux.web;

import org.namul.api.payload.core.web.WebResponseWrapper;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.http.HttpStatusCode;

/**
 * Reactive implementation of {@link WebResponseWrapper} using {@link ServerWebExchange}.
 * <p>
 * This wrapper provides access to the response metadata within a reactive pipeline,
 * allowing the core logic to inspect or modify the outgoing response.
 * @author Jeongmo Seo
 */
public class ReactiveWebResponseWrapper implements WebResponseWrapper {

    private final ServerWebExchange serverWebExchange;

    /**
     * Constructs a new wrapper using the provided {@link ServerWebExchange}.
     * @param serverWebExchange the current reactive web exchange
     */
    public ReactiveWebResponseWrapper(ServerWebExchange serverWebExchange) {
        this.serverWebExchange = serverWebExchange;
    }

    /**
     * Factory method to create a new {@link ReactiveWebResponseWrapper} instance
     * by wrapping the provided {@link ServerWebExchange}.
     * <p>
     * This method provides a more readable and fluent way to instantiate the wrapper
     * without using the {@code new} keyword directly.
     *
     * @param serverWebExchange the current reactive web exchange to be wrapped
     * @return a new instance of {@link ReactiveWebResponseWrapper}
     */
    public static ReactiveWebResponseWrapper wrap(ServerWebExchange serverWebExchange) {
        return new ReactiveWebResponseWrapper(serverWebExchange);
    }

    /**
     * Retrieves the first header value for the given name from the response.
     * @param name the name of the header to retrieve
     * @return the header value if present; an empty string otherwise
     */
    @Override
    public String getHeader(String name) {
        String header = serverWebExchange.getResponse().getHeaders().getFirst(name);
        return (header != null) ? header : "";
    }

    /**
     * Returns the HTTP status code of the current response.
     * @return the numerical HTTP status code, defaults to 200 if not yet set
     */
    @Override
    public int getStatus() {
        HttpStatusCode status = serverWebExchange.getResponse().getStatusCode();
        return (status != null) ? status.value() : 200;
    }

    /**
     * Retrieves the 'Content-Type' header of the response.
     * @return the media type string of the response content
     */
    @Override
    public String getContentType() {
        return String.valueOf(serverWebExchange.getResponse().getHeaders().getContentType());
    }
}