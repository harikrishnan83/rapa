package org.rest.rapa;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
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
		String ret = "";
		getMethod.setURI(new URI(url, false));
		try {
			int statusCode = getHttpClient().executeMethod(getMethod);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Method failed: "
						+ getMethod.getStatusLine());
			}

			ret = new String(getMethod.getResponseBody());

		} finally {
			getMethod.releaseConnection();
		}
		return ret;
	}

	private HttpClientAdapter getHttpClient() {
		return httpClientAdapter;
	}

	void post(String content, String url, String contentType)
			throws HttpException, IOException {
		postMethod.setURI(new URI(url, false));
		postMethod.setRequestHeader("Content-type", contentType);
		postMethod.setRequestBody(content);

		try {
			int statusCode = getHttpClient().executeMethod(postMethod);

			if (statusCode != HttpStatus.SC_CREATED) {
				throw new RuntimeException("Method failed: "
						+ postMethod.getStatusLine());
			}

		} finally {
			postMethod.releaseConnection();
		}
	}

	void update(String xml, String url, String contentType)
			throws HttpException, IOException {
		putMethod.setURI(new URI(url, false));
		putMethod.setRequestHeader("Content-type", contentType);
		putMethod.setRequestBody(xml);

		try {
			int statusCode = getHttpClient().executeMethod(putMethod);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Method failed: "
						+ putMethod.getStatusLine());
			}

		} finally {
			putMethod.releaseConnection();
		}

	}

	void delete(String url) throws HttpException, IOException {
		deleteMethod.setURI(new URI(url, false));

		try {
			int statusCode = getHttpClient().executeMethod(deleteMethod);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Method failed: "
						+ deleteMethod.getStatusLine());
			}

		} finally {
			deleteMethod.releaseConnection();
		}
	}

}
