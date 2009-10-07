package org.rest.rapa;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;

import java.io.IOException;

public class HttpClientAdapterImpl implements HttpClientAdapter {

	private final HttpClient httpClient;

	public HttpClientAdapterImpl(String username, String password, String host,
			int port, String realm, String scheme) {
		httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setCredentials(
				new AuthScope(host, port, realm, scheme),
				new UsernamePasswordCredentials(username, password));
	}
	
	public int executeMethod(HttpMethod method) throws IOException {
		return httpClient.executeMethod(method);
	}

}
