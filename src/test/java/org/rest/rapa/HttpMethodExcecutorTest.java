package org.rest.rapa;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.io.IOException;

public class HttpMethodExcecutorTest {
	private static final String CONTENT = "content";
	private static final String CONTENT_TYPE = "contentType";
	private static final String URL = "url";
	private HttpClientAdapter mockHttpClientAdaptor;

	@Before
	public void setup() {
		mockHttpClientAdaptor = mock(HttpClientAdapter.class);
	}
	
	@Test
	public void testShouldExecuteGet() throws IOException {
		when(mockHttpClientAdaptor.executeMethod((GetMethod) anyObject())).thenReturn(HttpStatus.SC_OK);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.get(URL);
	}
	
	@Test(expected = RuntimeException.class)
	public void testShouldThrowAnExceptionWhenHTTPStatusIsNotOK()
			throws IOException {
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.get(URL);
	}

	@Test
	public void testShouldExecutePostMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod((PostMethod) anyObject())).thenReturn(HttpStatus.SC_CREATED);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.post(CONTENT, URL, CONTENT_TYPE);
	}

	@Test(expected = RuntimeException.class)
	public void testShouldThrowAnExceptionIfPostDataHTTPStatusIsNotCreated()
			throws IOException {
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.post(CONTENT, URL, CONTENT_TYPE);
	}

	@Test
	public void testShouldExecutePutMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod((PutMethod) anyObject())).thenReturn(HttpStatus.SC_OK);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.put(CONTENT, URL, CONTENT_TYPE);
	}

	@Test(expected = RuntimeException.class)
	public void testShouldThrowAnExceptionIfUpdateDataHTTPStatusIsNotAccepted()
			throws IOException {
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.put(CONTENT, URL, CONTENT_TYPE);
	}

	@Test
	public void testShouldExecuteDeleteMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod((DeleteMethod) anyObject())).thenReturn(HttpStatus.SC_OK);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.delete(URL);
	}

	@Test(expected = RuntimeException.class)
	public void testShouldThrowAnExceptionIfDeleteHTTPStatusIsNotOK()
			throws IOException {
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor);
		httpMethodExecutor.delete(URL);
	}
}
