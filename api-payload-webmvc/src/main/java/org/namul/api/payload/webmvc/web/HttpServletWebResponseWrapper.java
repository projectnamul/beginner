package org.namul.api.payload.webmvc.web;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.namul.api.payload.core.web.WebResponseWrapper;

/**
 * Implementation of {@link WebResponseWrapper} that delegates to an {@link HttpServletResponse}.
 * This wrapper provides easy access to HTTP-specific response details for error analysis and logging.
 */
public class HttpServletWebResponseWrapper implements WebResponseWrapper {

    private final HttpServletResponse httpServletResponse;

    /**
     * Constructs a new wrapper with the specified {@link HttpServletResponse}.
     *
     * @param httpServletResponse the underlying HTTP response
     */
    public HttpServletWebResponseWrapper(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    /**
     * Wraps an {@link HttpServletResponse} into an {@link HttpServletWebResponseWrapper}.
     *
     * @param httpServletResponse the HTTP response to wrap
     * @return a new wrapper instance
     */
    public static HttpServletWebResponseWrapper wrap(HttpServletResponse httpServletResponse) {
        return new HttpServletWebResponseWrapper(httpServletResponse);
    }

    /**
     * Wraps a {@link ServletResponse} after validating that it is an instance of {@link HttpServletResponse}.
     *
     * @param response the {@link ServletResponse} to be wrapped, typically from a Filter
     * @return a new instance of {@link HttpServletWebResponseWrapper}
     * @throws IllegalArgumentException if the provided response is not an {@link HttpServletResponse}
     */
    public static HttpServletWebResponseWrapper wrap(ServletResponse response) {
        if (response instanceof HttpServletResponse) {
            return new HttpServletWebResponseWrapper((HttpServletResponse) response);
        }

        throw new IllegalArgumentException(String.format(
                "HttpServletWebResponseWrapper requires an 'HttpServletResponse', but received: '%s'. " +
                        "This wrapper is designed for HTTP-specific operations.",
                response.getClass().getName()
        ));
    }

    @Override
    public String getContentType() {
        return httpServletResponse.getContentType();
    }

    @Override
    public int getStatus() {
        return httpServletResponse.getStatus();
    }

    @Override
    public String getHeader(String name) {
        return httpServletResponse.getHeader(name);
    }
}