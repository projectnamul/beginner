package org.namul.api.payload.core.web;

/**
 * The class that returns web response info for use by internal logic
 */
public interface WebResponseWrapper {

    /**
     * The method returns content type of response.
     * @return The content type of response. If it cannot be found, it returns null.
     */
    String getContentType();

    /**
     * The method return status code of response.
     * @return The status code of response, If it cannot be found, it returns -1.
     */
    int getStatus();

    /**
     * The method returns header value in response.
     * @param name The case-insensitive header name which you want to find.
     * @return The value of header in response. If it cannot be found, it returns null.
     */
    String getHeader(String name);

}
