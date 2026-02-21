package org.namul.api.payload.core.web.supports;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.core.web.WebRequestWrapper;

import java.util.Optional;

public class HttpServletWebRequestWrapper implements WebRequestWrapper {

    private final HttpServletRequest httpServletRequest;

    public HttpServletWebRequestWrapper(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
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
        if (cookies != null && cookies.length != 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
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
