package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The class for Error handling when error occurs.
 * @param <R> The return type after handling exception
 * @param <T> The exception type which will be handled
 */
public interface ExceptionAdvice<R, T extends Exception> {

    /**
     * Handle method when exception occurs
     * @param e The Exception type
     * @return The Response value after handling exception
     */
    R handle(T e, HttpServletRequest request, HttpServletResponse response);
}
