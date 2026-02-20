package org.namul.api.payload.web;

import java.util.Optional;

/**
 * The class which returns web request info for use by internal logic
 */
public interface WebRequestWrapper {

    /**
     * The method returns RequestURI (except scheme, host name, port only endpoint)
     * @return The endpoint of request
     */
    String getRequestURI();

    /**
     * The method returns HttpMethod of Request
     * @return HttpMethod (GET, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, HEAD)
     */
    String getMethod();

    /**
     * The method returns header value
     * @param header The header name that you want to find
     * @return If header exists, returns header value. If not, return null
     */
    String getHeader(String header);

    /**
     * The method returns parameter value
     * @param parameter The parameter name that you want to find
     * @return If parameter exists, returns parameter value. If not, return null
     */
    String getParameter(String parameter);

    /**
     * The method returns Cookie value
     * @param name The cookie name that you want to find
     * @return If cookie exists, returns cookie value. If not, return null
     */
    String getCookie(String name);

    /**
     * The method returns remote address
     * @return Optional cookie value. So, it can be null when not found in the request
     */
    Optional<String> getRemoteAddress();

    /**
     * The method returns user agent
     * @return Optional user agent value. So, it can be null when not found in the request
     */
    Optional<String> getUserAgent();
}
