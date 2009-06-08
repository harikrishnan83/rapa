package org.rest.rapa;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import java.io.IOException;

public class HttpMethodExecutor {

    private HttpClientAdapter httpClientAdapter;
    private DeleteMethod deleteMethod;
    private GetMethod getMethod;
    private PostMethod postMethod;
    private PutMethod putMethod;

    public HttpMethodExecutor(HttpClientAdapter httpClientAdapter,
                              GetMethod getMethod, PostMethod postMethod,
                              DeleteMethod deleteMethod, PutMethod putMethod) {
        this.httpClientAdapter = httpClientAdapter;
        this.deleteMethod = deleteMethod;
        this.getMethod = getMethod;
        this.postMethod = postMethod;
        this.putMethod = putMethod;
    }

    public String get(String url) throws HttpException, IOException {
        return execute(url, getMethod, HttpStatus.SC_OK);
    }

    void post(String content, String url, String contentType)
            throws HttpException, IOException {
        postMethod.setRequestHeader("Content-type", contentType);
        postMethod.setRequestBody(content);
        execute(url, postMethod, HttpStatus.SC_CREATED);
    }

    void update(String xml, String url, String contentType)
            throws HttpException, IOException {
        putMethod.setRequestHeader("Content-type", contentType);
        putMethod.setRequestBody(xml);
        execute(url, putMethod, HttpStatus.SC_OK);
    }

    void delete(String url) throws HttpException, IOException {
        execute(url, deleteMethod, HttpStatus.SC_OK);
    }

    private String execute(String url, HttpMethodBase method, int status_to_check) throws HttpException, IOException {
        method.setURI(new URI(url, false));
        try {
            int statusCode = httpClientAdapter.executeMethod(method);
            if (statusCode != status_to_check) {
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
