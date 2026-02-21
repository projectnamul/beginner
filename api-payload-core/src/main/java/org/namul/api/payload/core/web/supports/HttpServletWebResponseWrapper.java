package org.namul.api.payload.core.web.supports;

import jakarta.servlet.http.HttpServletResponse;
import org.namul.api.payload.core.web.WebResponseWrapper;

public class HttpServletWebResponseWrapper implements WebResponseWrapper {

    private final HttpServletResponse httpServletResponse;

    public HttpServletWebResponseWrapper(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
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
