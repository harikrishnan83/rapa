package org.rest.rapa;

import junit.framework.TestCase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.io.IOException;

public class HttpMethodExcecutorTest extends TestCase {
	private static final String URL = "http://someUrl.com";

	@Test
	public void testShouldReturnAStringWhenHTTPStatusIsOK()
			throws  IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final GetMethod mockGetMethod = mock(GetMethod.class);
		String expectedResponse = "Response";

		when(mockHttpClientAdapter.executeMethod(mockGetMethod)).thenReturn(
				HttpStatus.SC_OK);
		when(mockGetMethod.getResponseBody()).thenReturn(
				expectedResponse.getBytes());

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected GetMethod getMethod() {
                return mockGetMethod;
            }
        };
		String actualResponse = httpMethodExecutor.get(URL);
		assertEquals(expectedResponse, actualResponse);
		verify(mockHttpClientAdapter).executeMethod(mockGetMethod);
		verify(mockGetMethod).getResponseBody();
		verify(mockGetMethod).releaseConnection();
	}

	@Test
	public void testShouldThrowAnExceptionWhenHTTPStatusIsNotOK()
			throws  IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final GetMethod mockGetMethod = mock(GetMethod.class);
		when(mockHttpClientAdapter.executeMethod(mockGetMethod)).thenReturn(
				HttpStatus.SC_NOT_FOUND);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected GetMethod getMethod() {
                return mockGetMethod;
            }
        };
		try {

			httpMethodExecutor.get(URL);
			fail("Exception was not thrown when http status was not OK");
		} catch (Exception e) {

		}
		verify(mockHttpClientAdapter).executeMethod(mockGetMethod);
		verify(mockGetMethod).releaseConnection();
	}

	@Test
	public void testShouldPostDataToURL() throws  IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final PostMethod mockPostMethod = mock(PostMethod.class);

		when(mockHttpClientAdapter.executeMethod(mockPostMethod)).thenReturn(
				HttpStatus.SC_CREATED);

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected PostMethod postMethod() {
                return mockPostMethod;
            }
        };
        httpMethodExecutor.post("<Data></Data>", URL, "json");
		verify(mockHttpClientAdapter).executeMethod(mockPostMethod);
		verify(mockPostMethod).setRequestHeader("Content-type", "json");
		verify(mockPostMethod).releaseConnection();
	}

	@Test
	public void testShouldThrowAnExceptionIfPostDataHTTPStatusIsNotCreated()
			throws  IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final PostMethod mockPostMethod = mock(PostMethod.class);

		when(mockHttpClientAdapter.executeMethod(mockPostMethod)).thenReturn(
				HttpStatus.SC_CONFLICT);

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected PostMethod postMethod() {
                return mockPostMethod;
            }
        };
		String data = "<Data></Data>";
		try {
			httpMethodExecutor.post(data, URL, "json");
			fail("Exception was not thrown when HTTPStatus was not CREATED.");
		} catch (Exception e) {

		}
		verify(mockHttpClientAdapter).executeMethod(mockPostMethod);
		verify(mockPostMethod).setRequestHeader("Content-type", "json");
		verify(mockPostMethod).releaseConnection();
	}

	@Test
	public void testShouldUpdateDataToURL() throws  IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final PutMethod mockPutMethod = mock(PutMethod.class);

		when(mockHttpClientAdapter.executeMethod(mockPutMethod)).thenReturn(
				HttpStatus.SC_OK);

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected PutMethod putMethod() {
                return mockPutMethod;
            }
        };
		String data = "<Data></Data>";
		httpMethodExecutor.update(data, URL, "json");
		verify(mockHttpClientAdapter).executeMethod(mockPutMethod);
		verify(mockPutMethod).setRequestHeader("Content-type", "json");
		verify(mockPutMethod).releaseConnection();
	}

	@Test
	public void testShouldThrowAnExceptionIfUpdateDataHTTPStatusIsNotAccepted()
			throws  IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final PutMethod mockPutMethod = mock(PutMethod.class);

		when(mockHttpClientAdapter.executeMethod(mockPutMethod)).thenReturn(
				HttpStatus.SC_CONFLICT);

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected PutMethod putMethod() {
                return mockPutMethod;
            }
        };
		String data = "<Data></Data>";
		try {
			httpMethodExecutor.update(data, URL, "json");
			fail("Exception was not thrown when HTTPStatus was not CREATED.");
		} catch (Exception e) {

		}
		verify(mockHttpClientAdapter).executeMethod(mockPutMethod);
		verify(mockPutMethod).setRequestHeader("Content-type", "json");
		verify(mockPutMethod).releaseConnection();
	}

	@Test
	public void testShouldDeleteResourceAtURL() throws
			IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final DeleteMethod mockDeleteMethod = mock(DeleteMethod.class);

		when(mockHttpClientAdapter.executeMethod(mockDeleteMethod)).thenReturn(
				HttpStatus.SC_OK);

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected DeleteMethod deleteMethod() {
                return mockDeleteMethod;
            }
        };
		httpMethodExecutor.delete(URL);
		verify(mockHttpClientAdapter).executeMethod(mockDeleteMethod);
		verify(mockDeleteMethod).releaseConnection();
	}

	@Test
	public void testShouldThrowAnExceptionIfDeleteHTTPStatusIsNotOK()
			throws  IOException {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		final DeleteMethod mockDeleteMethod = mock(DeleteMethod.class);

		when(mockHttpClientAdapter.executeMethod(mockDeleteMethod)).thenReturn(
				HttpStatus.SC_NOT_FOUND);

		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(mockHttpClientAdapter) {
            protected DeleteMethod deleteMethod() {
                return mockDeleteMethod;
            }
        };
		try {
			httpMethodExecutor.delete(URL);
			fail("Exception was not thrown when HTTPStatus was not OK.");
		} catch (Exception e) {

		}
		verify(mockHttpClientAdapter).executeMethod(mockDeleteMethod);
		verify(mockDeleteMethod).releaseConnection();
	}
}
