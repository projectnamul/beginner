package org.namul.api.payload.web;

public interface WebResponseWrapper {

    String getContentType();
    int getStatus();
    String getHeader(String name);

}
