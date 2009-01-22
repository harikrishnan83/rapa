package org.rest.rapa;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;

public class HttpClientAdapterImpl implements HttpClientAdapter {
	
	private HttpClient httpClient;

	public HttpClientAdapterImpl(String username, String password, String host, int port, String realm, String scheme) {
		httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setCredentials(
				new AuthScope(host, port, realm, scheme),
				new UsernamePasswordCredentials(username, password));
		/*httpClient.getState().setCredentials(
				new AuthScope("localhost", 9000, AuthScope.ANY_REALM),
				new UsernamePasswordCredentials(username, password));*/
	}
	
	public HttpClientAdapterImpl(String username, String password, String host, int port, String realm) {
		httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setCredentials(
				new AuthScope(host, port, realm),
				new UsernamePasswordCredentials(username, password));
		/*httpClient.getState().setCredentials(
				new AuthScope("localhost", 9000, AuthScope.ANY_REALM),
				new UsernamePasswordCredentials(username, password));*/
	}
	
	public HttpClientAdapterImpl(String username, String password, String host, int port) {
		httpClient = new HttpClient();
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setCredentials(
				new AuthScope(host, port),
				new UsernamePasswordCredentials(username, password));
		/*httpClient.getState().setCredentials(
				new AuthScope("localhost", 9000, AuthScope.ANY_REALM),
				new UsernamePasswordCredentials(username, password));*/
	}

	@Override
	public int executeMethod(HttpMethod method) throws HttpException, IOException {
		return httpClient.executeMethod(method);
	}

}
