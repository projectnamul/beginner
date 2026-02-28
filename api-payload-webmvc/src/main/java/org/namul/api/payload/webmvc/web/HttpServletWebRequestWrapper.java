package org.namul.api.payload.webmvc.web;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.core.web.WebRequestWrapper;

import java.util.Optional;

/**
 * Implementation of {@link WebRequestWrapper} that delegates to an {@link HttpServletRequest}.
 * This wrapper provides easy access to HTTP-specific information for error handling and logging.
 */
public class HttpServletWebRequestWrapper implements WebRequestWrapper {

    private final HttpServletRequest httpServletRequest;

    /**
     * Constructs a new wrapper with the specified {@link HttpServletRequest}.
     * * @param httpServletRequest the underlying HTTP request
     */
    public HttpServletWebRequestWrapper(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * Wraps an {@link HttpServletRequest} into an {@link HttpServletWebRequestWrapper}.
     * * @param httpServletRequest the HTTP request to wrap
     * @return a new wrapper instance
     */
    public static HttpServletWebRequestWrapper wrap(HttpServletRequest httpServletRequest) {
        return new HttpServletWebRequestWrapper(httpServletRequest);
    }

    /**
     * Wraps a {@link ServletRequest} after validating that it is an instance of {@link HttpServletRequest}.
     *
     * @param servletRequest the {@link ServletRequest} to be wrapped, typically from a Filter
     * @return a new instance of {@link HttpServletWebRequestWrapper}
     * @throws IllegalArgumentException if the provided request is not an {@link HttpServletRequest}
     */
    public static HttpServletWebRequestWrapper wrap(ServletRequest servletRequest) {
        if (servletRequest instanceof HttpServletRequest) {
            return new HttpServletWebRequestWrapper((HttpServletRequest) servletRequest);
        }
        throw new IllegalArgumentException(String.format(
                "HttpServletWebRequestWrapper requires an 'HttpServletRequest', but received: '%s'. " +
                        "This wrapper is designed for HTTP-specific operations (Headers, Cookies, Method, etc.).",
                servletRequest.getClass().getName()
        ));
    }

    @Override
    public String getHeader(String header) {
        return httpServletRequest.getHeader(header);
    }

    @Override
    public String getMethod() {
        return httpServletRequest.getMethod();
    }

    @Override
    public String getRequestURI() {
        return httpServletRequest.getRequestURI();
    }

    @Override
    public String getParameter(String parameter) {
        return httpServletRequest.getParameter(parameter);
    }

    @Override
    public String getCookie(String name) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Optional<String> getRemoteAddress() {
        return Optional.ofNullable(httpServletRequest.getRemoteAddr());
    }

    @Override
    public Optional<String> getUserAgent() {
        return Optional.ofNullable(httpServletRequest.getHeader("User-Agent"));
    }
}