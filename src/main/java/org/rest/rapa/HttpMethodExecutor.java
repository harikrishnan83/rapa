package org.rest.rapa;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.*;

import java.io.IOException;

public class HttpMethodExecutor {

    private final HttpClientAdapter httpClientAdapter;
    private final DeleteMethod deleteMethod;
    private final GetMethod getMethod;
    private final PostMethod postMethod;
    private final PutMethod putMethod;

    public HttpMethodExecutor(HttpClientAdapter httpClientAdapter,
                              GetMethod getMethod, PostMethod postMethod,
                              DeleteMethod deleteMethod, PutMethod putMethod) {
        this.httpClientAdapter = httpClientAdapter;
        this.deleteMethod = deleteMethod;
        this.getMethod = getMethod;
        this.postMethod = postMethod;
        this.putMethod = putMethod;
    }

    public String get(String url) throws  IOException {
        return execute(url, getMethod, HttpStatus.SC_OK);
    }

    void post(String content, String url, String contentType) throws IOException {
        postMethod.setRequestHeader("Content-type", contentType);
        postMethod.setRequestEntity(new StringRequestEntity(content, contentType, "UTF-8"));
        execute(url, postMethod, HttpStatus.SC_CREATED);
    }

    void update(String xml, String url, String contentType) throws IOException {
        putMethod.setRequestHeader("Content-type", contentType);
        putMethod.setRequestEntity(new StringRequestEntity(xml, contentType, "UTF-8"));
        execute(url, putMethod, HttpStatus.SC_OK);
    }

    void delete(String url) throws IOException {
        execute(url, deleteMethod, HttpStatus.SC_OK);
    }

    private String execute(String url, HttpMethodBase method, int statusToCheck) throws IOException {
        method.setURI(new URI(url, false));
        try {
            int statusCode = httpClientAdapter.executeMethod(method);
            if (statusCode != statusToCheck) {
                throw new RuntimeException("Method failed: "
                        + method.getStatusLine());
            }
            byte[] responseBody = method.getResponseBody();
            return  responseBody !=null ? new String(responseBody) : "";
        } finally {
            method.releaseConnection();
        }
    }
}
