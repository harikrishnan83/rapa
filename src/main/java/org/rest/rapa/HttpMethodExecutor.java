package org.rest.rapa;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.*;

import java.io.IOException;

public class HttpMethodExecutor {

	private static final String METHOD_FAILED = "Method failed: ";
	private static final String UTF_8 = "UTF-8";
	private static final String CONTENT_TYPE = "Content-type";
	private final HttpClientAdapter httpClientAdapter;

	public HttpMethodExecutor(HttpClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}

	public String get(String url) throws IOException {
		return execute(url, getMethod(), HttpStatus.SC_OK);
	}

	void post(String content, String url, String contentType)
			throws IOException {
		PostMethod postMethod = postMethod();
		postMethod.setRequestHeader(CONTENT_TYPE, contentType);
		postMethod.setRequestEntity(new StringRequestEntity(content,
				contentType, UTF_8));
		execute(url, postMethod, HttpStatus.SC_CREATED);
	}

	void put(String xml, String url, String contentType) throws IOException {
		PutMethod putMethod = putMethod();
		putMethod.setRequestHeader(CONTENT_TYPE, contentType);
		putMethod.setRequestEntity(new StringRequestEntity(xml, contentType,
				UTF_8));
		execute(url, putMethod, HttpStatus.SC_OK);
	}

	void delete(String url) throws IOException {
		execute(url, deleteMethod(), HttpStatus.SC_OK);
	}

	protected GetMethod getMethod() {
		return new GetMethod();
	}

	protected PostMethod postMethod() {
		return new PostMethod();
	}

	protected PutMethod putMethod() {
		return new PutMethod();
	}

	protected DeleteMethod deleteMethod() {
		return new DeleteMethod();
	}

	private String execute(String url, HttpMethodBase method, int statusToCheck)
			throws IOException {
		method.setURI(new URI(url, false));
		try {
			int statusCode = httpClientAdapter.executeMethod(method);
			if (statusCode != statusToCheck) {
				throw new RuntimeException(METHOD_FAILED
						+ method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();
			return responseBody != null ? new String(responseBody) : "";
		} finally {
			method.releaseConnection();
		}
	}
}
