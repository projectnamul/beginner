package org.namul.api.payload.core.web;

import java.util.Optional;

/**
 * A technology-agnostic wrapper for retrieving metadata from an incoming web request.
 * <p>
 * This interface abstracts the underlying request implementation (e.g., HttpServletRequest,
 * ServerHttpRequest), allowing internal logic to access request details without
 * direct dependency on a specific web framework.
 */
public interface WebRequestWrapper {

    /**
     * Returns the request URI path, excluding the scheme, host name, and port.
     * * @return the request endpoint (e.g., "/api/v1/articles").
     */
    String getRequestURI();

    /**
     * Returns the HTTP method of the request.
     * * @return the HTTP method string (e.g., GET, POST, PUT, DELETE).
     */
    String getMethod();

    /**
     * Retrieves the value of the specified request header.
     * * @param header the name of the header to retrieve.
     * @return the header value if present; {@code null} otherwise.
     */
    String getHeader(String header);

    /**
     * Retrieves the value of a specific request parameter.
     * * @param parameter the name of the parameter to retrieve.
     * @return the parameter value if present; {@code null} otherwise.
     */
    String getParameter(String parameter);

    /**
     * Retrieves the value of a specific cookie by its name.
     * * @param name the name of the cookie to retrieve.
     * @return the cookie value if present; {@code null} otherwise.
     */
    String getCookie(String name);

    /**
     * Returns the remote IP address of the client that sent the request.
     * * @return an {@link Optional} containing the remote address, or empty if unavailable.
     */
    Optional<String> getRemoteAddress();

    /**
     * Returns the 'User-Agent' header value from the request.
     * * @return an {@link Optional} containing the user agent string, or empty if not found.
     */
    Optional<String> getUserAgent();
}