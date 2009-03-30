package org.rest.rapa;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

public class HttpMethodExecutor {

	private MethodFactory methodFactory;
	private HttpClientAdapter httpClientAdapter;

	public HttpMethodExecutor(MethodFactory methodFactory,
			HttpClientAdapter httpClientAdapter) {
		this.methodFactory = methodFactory;
		this.httpClientAdapter = httpClientAdapter;
	}

	String getXML(String resourceSpecificURL) throws HttpException, IOException {
		String ret = "";
		GetMethod method = methodFactory.createGetMethod(resourceSpecificURL);
		try {
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

			ret = new String(method.getResponseBody());

		} finally {
			method.releaseConnection();
		}
		return ret;
	}

	private HttpClientAdapter getHttpClient() {
		return httpClientAdapter;
	}

	String postXML(String xml, String url) throws HttpException, IOException {
		String ret = "";
		PostMethod method = methodFactory.createPostMethod(url);
		method.setRequestHeader("Content-type", "text/xml");
		method.setRequestBody(xml);

		try {
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_CREATED) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

		} finally {
			method.releaseConnection();
		}

		return ret;
	}

	String updateXML(String xml, String url) throws HttpException, IOException {
		String ret = "";
		PutMethod method = methodFactory.createPutMethod(url);
		method.setRequestHeader("Content-type", "text/xml");
		method.setRequestBody(xml);

		try {
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_ACCEPTED) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

		} finally {
			method.releaseConnection();
		}

		return ret;
	}

	void deleteXML(String url) throws HttpException, IOException {
		DeleteMethod method = methodFactory.createDeleteMethod(url);

		try {
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

		} finally {
			method.releaseConnection();
		}
	}

}
