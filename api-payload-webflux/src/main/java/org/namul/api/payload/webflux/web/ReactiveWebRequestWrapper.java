package org.namul.api.payload.webflux.web;

import org.namul.api.payload.core.web.WebRequestWrapper;
import org.springframework.http.HttpCookie;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

/**
 * Reactive implementation of {@link WebRequestWrapper} using {@link ServerWebExchange}.
 * <p>
 * This wrapper adapts the Spring WebFlux request model to the technology-agnostic
 * interface, enabling core logic to function in a non-blocking environment.
 *
 * @author Jeongmo Seo
 */
public class ReactiveWebRequestWrapper implements WebRequestWrapper {

    private final ServerWebExchange serverWebExchange;

    /**
     * Constructs a new wrapper using the provided {@link ServerWebExchange}.
     *
     * @param serverWebExchange the current reactive web exchange
     */
    public ReactiveWebRequestWrapper(ServerWebExchange serverWebExchange) {
        this.serverWebExchange = serverWebExchange;
    }

    /**
     * Factory method to create a new {@link ReactiveWebRequestWrapper} instance
     * by wrapping the provided {@link ServerWebExchange}.
     * <p>
     * This method provides a more readable and fluent way to instantiate the wrapper
     * without using the {@code new} keyword directly.
     *
     * @param serverWebExchange the current reactive web exchange to be wrapped
     * @return a new instance of {@link ReactiveWebRequestWrapper}
     */
    public static ReactiveWebRequestWrapper wrap(ServerWebExchange serverWebExchange) {
        return new ReactiveWebRequestWrapper(serverWebExchange);
    }

    /**
     * Returns the request URI path from the reactive request.
     *
     * @return the request endpoint path (e.g., "/api/v1/resource")
     */
    @Override
    public String getRequestURI() {
        return serverWebExchange.getRequest().getPath().value();
    }

    /**
     * Retrieves the value of a specific cookie by its name from the reactive request.
     *
     * @param name the name of the cookie to retrieve
     * @return the cookie value if present; {@code null} otherwise
     */
    @Override
    public String getCookie(String name) {
        HttpCookie cookie = serverWebExchange.getRequest().getCookies().getFirst(name);
        return (cookie != null) ? cookie.getValue() : null;
    }

    /**
     * Returns the HTTP method of the reactive request.
     *
     * @return the HTTP method string (e.g., "GET", "POST")
     */
    @Override
    public String getMethod() {
        return serverWebExchange.getRequest().getMethod().name();
    }

    /**
     * Retrieves the first header value for the given name from the reactive request.
     *
     * @param header the name of the header to retrieve
     * @return the header value if present; {@code null} otherwise
     */
    @Override
    public String getHeader(String header) {
        return serverWebExchange.getRequest().getHeaders().getFirst(header);
    }

    /**
     * Retrieves the first query parameter value for the given name.
     *
     * @param parameter the name of the parameter to retrieve
     * @return the parameter value if present; {@code null} otherwise
     */
    @Override
    public String getParameter(String parameter) {
        return serverWebExchange.getRequest().getQueryParams().getFirst(parameter);
    }

    /**
     * Returns the 'User-Agent' header value directly from the reactive request headers.
     *
     * @return an {@link Optional} containing the user agent string, or empty if not found
     */
    @Override
    public Optional<String> getUserAgent() {
        return Optional.ofNullable(serverWebExchange.getRequest().getHeaders().getFirst("User-Agent"));
    }

    /**
     * Returns the remote IP address of the client from the reactive request.
     *
     * @return an {@link Optional} containing the remote host address, or empty if unavailable
     */
    @Override
    public Optional<String> getRemoteAddress() {
        return Optional.ofNullable(serverWebExchange.getRequest().getRemoteAddress())
                .map(address -> address.getAddress().getHostAddress());
    }
}