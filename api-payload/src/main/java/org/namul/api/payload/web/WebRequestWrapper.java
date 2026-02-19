package org.namul.api.payload.web;

import java.util.Optional;

public interface WebRequestWrapper {

    String getRequestURI();
    String getMethod();
    String getHeader(String header);
    String getParameter(String parameter);
    String getCookie(String name);
    Optional<String> getRemoteAddress();
    Optional<String> getUserAgent();
}
