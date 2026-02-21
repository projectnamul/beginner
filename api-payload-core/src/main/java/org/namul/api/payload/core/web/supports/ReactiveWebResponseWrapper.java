package org.namul.api.payload.core.web.supports;

import org.namul.api.payload.core.web.WebResponseWrapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ServerWebExchange;

public class ReactiveWebResponseWrapper implements WebResponseWrapper {

    private final ServerWebExchange serverWebExchange;

    public ReactiveWebResponseWrapper(ServerWebExchange serverWebExchange) {
        this.serverWebExchange = serverWebExchange;
    }

    @Override
    public String getHeader(String name) {
        return serverWebExchange.getResponse().getHeaders().getFirst(name);
    }

    @Override
    public String getContentType() {
        return serverWebExchange.getResponse().getHeaders().getFirst("Content-Type");
    }

    @Override
    public int getStatus() {
        HttpStatusCode code = serverWebExchange.getResponse().getStatusCode();
        if (code == null) {
            return -1;
        }
        return code.value();
    }

}
