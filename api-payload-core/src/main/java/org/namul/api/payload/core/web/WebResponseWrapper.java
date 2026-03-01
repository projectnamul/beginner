package org.namul.api.payload.core.web;

/**
 * A technology-agnostic wrapper for retrieving metadata from an outgoing web response.
 * <p>
 * This interface abstracts the underlying response implementation, allowing
 * internal logic to inspect response details (e.g., status codes, headers)
 * independently of the specific web framework being used.
 */
public interface WebResponseWrapper {

    /**
     * Returns the 'Content-Type' header value of the response.
     * * @return the content type string (e.g., "application/json");
     * {@code null} if the content type has not been set.
     */
    String getContentType();

    /**
     * Returns the HTTP status code of the current response.
     * * @return the integer status code (e.g., 200, 404);
     * {@code -1} if the status code cannot be determined.
     */
    int getStatus();

    /**
     * Retrieves the value of the specified response header.
     * * @param name the case-insensitive name of the header to retrieve.
     * @return the value of the header; {@code null} if the header is not present.
     */
    String getHeader(String name);

}