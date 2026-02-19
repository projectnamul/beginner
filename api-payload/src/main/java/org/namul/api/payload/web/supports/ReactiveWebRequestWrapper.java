package org.namul.api.payload.web.supports;

import org.namul.api.payload.web.WebRequestWrapper;
import org.springframework.http.HttpCookie;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;
import java.util.Optional;

public class ReactiveWebRequestWrapper implements WebRequestWrapper {

    private final ServerWebExchange serverWebExchange;

    public ReactiveWebRequestWrapper(ServerWebExchange serverWebExchange) {
        this.serverWebExchange = serverWebExchange;
    }

    @Override
    public String getRequestURI() {
        return serverWebExchange.getRequest().getURI().toString();
    }

    @Override
    public String getMethod() {
        return serverWebExchange.getRequest().getMethod().name();
    }

    @Override
    public String getHeader(String header) {
        return serverWebExchange.getRequest().getHeaders().getFirst(header);
    }

    @Override
    public String getParameter(String parameter) {
        return serverWebExchange.getRequest().getQueryParams().getFirst(parameter);
    }

    @Override
    public String getCookie(String name) {
        HttpCookie cookie = serverWebExchange.getRequest().getCookies().getFirst(name);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    @Override
    public Optional<String> getRemoteAddress() {
        InetSocketAddress remoteAddress = serverWebExchange.getRequest().getRemoteAddress();
        if (remoteAddress == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(remoteAddress.getHostName());
    }

    @Override
    public Optional<String> getUserAgent() {
        return Optional.ofNullable(serverWebExchange.getRequest().getHeaders().getFirst("User-Agent"));
    }
}
