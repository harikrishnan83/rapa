package org.rest.rapa;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.httpclient.Header;
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
import java.util.HashMap;
import java.util.Map;

public class HttpMethodExcecutorTest {
	private static final String CONTENT = "content";
	private static final String CONTENT_TYPE = "contentType";
	private static final String URL = "url";
	private HttpClientAdapter mockHttpClientAdaptor;
	private HttpMethodProvider mockHttpMethodProvider;
	private Ehcache mockCache;
	private CacheManager mockCacheManager;
	private Map<String, String> emptyRequestHeaders = new HashMap<String, String> ();

	@Before
	public void setup() {
		mockHttpClientAdaptor = mock(HttpClientAdapter.class);
		mockHttpMethodProvider = mock(HttpMethodProvider.class);
		mockCache = mock(Ehcache.class);
		mockCacheManager = mock(CacheManager.class);
	}

	@Test
	public void shouldRetrieveElementFromCacheIfPresent() throws IOException {
		when(mockCache.get(URL)).thenReturn(new Element(URL, "resource"));
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.get(URL, emptyRequestHeaders);
	}

	@Test
	public void shouldExecuteGetMethodIfResourceNotCached() throws IOException {
		when(mockCache.get(URL)).thenReturn(null);
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class)))
				.thenReturn(HttpStatus.SC_OK);

		String cacheControlHeaderValue = "max-age=0, public";
		when(mockHttpMethodProvider.getMethod(emptyRequestHeaders)).thenReturn(
				createGetMethod(cacheControlHeaderValue));

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.get(URL, emptyRequestHeaders);
	}

	@Test
	public void shouldCacheResourceIfMaxAgeIsGreaterThanZero()
			throws IOException {
		when(mockCache.get(URL)).thenReturn(null);

		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class)))
				.thenReturn(HttpStatus.SC_OK);

		String cacheControlHeaderValue = "max-age=1, public";
		when(mockHttpMethodProvider.getMethod(emptyRequestHeaders)).thenReturn(
				createGetMethod(cacheControlHeaderValue));

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);

		httpMethodExecutor.get(URL, emptyRequestHeaders);

		verify(mockCache).put(any(Element.class));
	}

	@Test
	public void shouldNotCacheResourceIfMaxAgeIsLessThanOne()
			throws IOException {
		when(mockCache.get(URL)).thenReturn(null);

		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class)))
				.thenReturn(HttpStatus.SC_OK);

		String cacheControlHeaderValue = "max-age=0, public";
		when(mockHttpMethodProvider.getMethod(emptyRequestHeaders)).thenReturn(
				createGetMethod(cacheControlHeaderValue));

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);

		httpMethodExecutor.get(URL, emptyRequestHeaders);

		verify(mockCache, never()).put(any(Element.class));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionWhenHTTPStatusIsNotOK()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class)))
				.thenReturn(HttpStatus.SC_NOT_FOUND);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.get(URL, emptyRequestHeaders);
	}

	@Test
	public void shouldExecutePostMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(PostMethod.class)))
				.thenReturn(HttpStatus.SC_CREATED);
		when(mockHttpMethodProvider.postMethod()).thenReturn(new PostMethod());
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.post(CONTENT, URL, CONTENT_TYPE);
	}

	@Test
	public void shouldReturnReturnPostMethodResponseBody() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(PostMethod.class)))
				.thenReturn(HttpStatus.SC_CREATED);
		PostMethod postMethod = new PostMethod() {
			public byte[] getResponseBody() {
				return new String("expectedResponse").getBytes();
			}
		};
		when(mockHttpMethodProvider.postMethod()).thenReturn(postMethod);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);

		assertEquals("expectedResponse", httpMethodExecutor.post(CONTENT, URL,
				CONTENT_TYPE));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionIfPostDataHTTPStatusIsNotCreated()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class)))
				.thenReturn(HttpStatus.SC_FORBIDDEN);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.post(CONTENT, URL, CONTENT_TYPE);
	}

	@Test
	public void testShouldExecutePutMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(PutMethod.class)))
				.thenReturn(HttpStatus.SC_OK);
		when(mockHttpMethodProvider.putMethod()).thenReturn(new PutMethod());
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.put(CONTENT, URL, CONTENT_TYPE);
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionIfUpdateDataHTTPStatusIsNotAccepted()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class)))
				.thenReturn(HttpStatus.SC_FORBIDDEN);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.put(CONTENT, URL, CONTENT_TYPE);
	}

	@Test
	public void shouldExecuteDeleteMethod() throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(DeleteMethod.class)))
				.thenReturn(HttpStatus.SC_OK);
		when(mockHttpMethodProvider.deleteMethod()).thenReturn(
				new DeleteMethod());
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.delete(URL);
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionIfDeleteHTTPStatusIsNotOK()
			throws IOException {
		when(mockHttpClientAdaptor.executeMethod(any(GetMethod.class)))
				.thenReturn(HttpStatus.SC_FORBIDDEN);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				mockHttpClientAdaptor, mockHttpMethodProvider, mockCache,
				mockCacheManager);
		httpMethodExecutor.delete(URL);
	}

	private GetMethod createGetMethod(final String cacheControlHeaderValue) {
		return new GetMethod() {
			@Override
			public Header getResponseHeader(String headerName) {
				Header header = new Header();
				header.setName("Cache-Control");
				header.setValue(cacheControlHeaderValue);
				return header;
			}
		};
	}
}
