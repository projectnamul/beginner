package org.namul.api.payload.webflux.web;

import org.namul.api.payload.core.web.WebRequestWrapper;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Optional;

/**
 * Reactive implementation of {@link WebRequestWrapper} using {@link ServerHttpRequest}.
 * <p>
 * This wrapper adapts the Spring WebFlux request model to the technology-agnostic
 * interface by focusing strictly on the request components.
 *
 * @author Jeongmo Seo
 */
public class ReactiveWebRequestWrapper implements WebRequestWrapper {

    private final ServerHttpRequest request;

    /**
     * Constructs a new wrapper using the provided {@link ServerHttpRequest}.
     *
     * @param request the current reactive HTTP request
     */
    public ReactiveWebRequestWrapper(ServerHttpRequest request) {
        this.request = request;
    }

    /**
     * Factory method to create a new {@link ReactiveWebRequestWrapper} instance.
     *
     * @param request the current reactive HTTP request to be wrapped
     * @return a new instance of {@link ReactiveWebRequestWrapper}
     */
    public static ReactiveWebRequestWrapper wrap(ServerHttpRequest request) {
        return new ReactiveWebRequestWrapper(request);
    }

    @Override
    public String getRequestURI() {
        return request.getPath().value();
    }

    @Override
    public String getCookie(String name) {
        HttpCookie cookie = request.getCookies().getFirst(name);
        return (cookie != null) ? cookie.getValue() : null;
    }

    @Override
    public String getMethod() {
        return request.getMethod().name();
    }

    @Override
    public String getHeader(String header) {
        return request.getHeaders().getFirst(header);
    }

    @Override
    public String getParameter(String parameter) {
        return request.getQueryParams().getFirst(parameter);
    }

    @Override
    public Optional<String> getUserAgent() {
        return Optional.ofNullable(request.getHeaders().getFirst("User-Agent"));
    }

    @Override
    public Optional<String> getRemoteAddress() {
        return Optional.ofNullable(request.getRemoteAddress())
                .map(address -> address.getAddress().getHostAddress());
    }
}