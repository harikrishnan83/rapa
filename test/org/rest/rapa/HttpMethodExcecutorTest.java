package org.rest.rapa;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

public class HttpMethodExcecutorTest {
	private static final String CONTENT = "content";
	private static final String CONTENT_TYPE = "contentType";
	private static final String URL = "url";
	private HttpClientAdapter mockHttpClientAdaptor;
	private HttpMethodProvider mockHttpMethodProvider;

	@Before
	public void setup() {
		mockHttpClientAdaptor = mock(HttpClientAdapter.class);
		mockHttpMethodProvider = mock(HttpMethodProvider.class);
		
	}
	
	@Test
	public void shouldExecuteGet() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class))).thenReturn(HttpStatus.SC_OK);
		when(mockHttpMethodProvider.getMethod()).thenReturn(new GetMethod());
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		httpMethodExecutor.get(URL);
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionWhenHTTPStatusIsNotOK()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class))).thenReturn(HttpStatus.SC_NOT_FOUND);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		httpMethodExecutor.get(URL);
	}

	@Test
	public void shouldExecutePostMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(PostMethod.class))).thenReturn(HttpStatus.SC_CREATED);
		when(mockHttpMethodProvider.postMethod()).thenReturn(new PostMethod());
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		httpMethodExecutor.post(CONTENT, URL, CONTENT_TYPE);
	}
	
	@Test
	public void shouldReturnReturnPostMethodResponseBody() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(PostMethod.class))).thenReturn(HttpStatus.SC_CREATED);
		PostMethod postMethod = new PostMethod() {
			public byte[] getResponseBody() {
				return new String("expectedResponse").getBytes();
			}
		};
		when(mockHttpMethodProvider.postMethod()).thenReturn(postMethod);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		
	    assertEquals("expectedResponse", httpMethodExecutor.post(CONTENT, URL, CONTENT_TYPE));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionIfPostDataHTTPStatusIsNotCreated()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class))).thenReturn(HttpStatus.SC_FORBIDDEN);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		httpMethodExecutor.post(CONTENT, URL, CONTENT_TYPE);
	}

	@Test
	public void testShouldExecutePutMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(PutMethod.class))).thenReturn(HttpStatus.SC_OK);
		when(mockHttpMethodProvider.putMethod()).thenReturn(new PutMethod());
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		httpMethodExecutor.put(CONTENT, URL, CONTENT_TYPE);
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionIfUpdateDataHTTPStatusIsNotAccepted()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class))).thenReturn(HttpStatus.SC_FORBIDDEN);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		httpMethodExecutor.put(CONTENT, URL, CONTENT_TYPE);
	}

	@Test
	public void shouldExecuteDeleteMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(DeleteMethod.class))).thenReturn(HttpStatus.SC_OK);
		when(mockHttpMethodProvider.deleteMethod()).thenReturn(new DeleteMethod());
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor,mockHttpMethodProvider);
		httpMethodExecutor.delete(URL);
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionIfDeleteHTTPStatusIsNotOK()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class))).thenReturn(HttpStatus.SC_FORBIDDEN);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdaptor, mockHttpMethodProvider);
		httpMethodExecutor.delete(URL);
	}
}
