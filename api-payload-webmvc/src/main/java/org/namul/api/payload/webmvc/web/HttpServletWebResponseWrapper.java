package org.namul.api.payload.webmvc.web;

import jakarta.servlet.http.HttpServletResponse;
import org.namul.api.payload.core.web.WebResponseWrapper;

public class HttpServletWebResponseWrapper implements WebResponseWrapper {

    private final HttpServletResponse httpServletResponse;

    public HttpServletWebResponseWrapper(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public static HttpServletWebResponseWrapper wrap(HttpServletResponse httpServletResponse) {
        return new HttpServletWebResponseWrapper(httpServletResponse);
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
