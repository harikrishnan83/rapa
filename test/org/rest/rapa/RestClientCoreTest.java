package org.rest.rapa;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.junit.Test;
import org.rest.rapa.HttpClientAdapter;
import org.rest.rapa.MethodFactory;
import org.rest.rapa.RestClientCore;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;
import org.rest.rapa.resource.ResourceUtil;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RestClientCoreTest extends TestCase {

	private static final String URL = "url";

	@Test
	public void testShouldThrowExceptionIfArgumentsAreInvalid() {
		try {
			RestClientCore restClient = new RestClientCore("");
			fail("Did not throw exception when arguments were null or empty string");
		} catch (IllegalArgumentException illegalArgumentException) {
			// expected
		}
	}

	@Test
	public void testShouldGetResourceById() {
		RestClientCore restClient = restClient(URL, "username", "password");
		
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		GetMethod mockGetMethod = mock(GetMethod.class);
		MethodFactory mockMethodFactory = mock(MethodFactory.class);
		when(mockMethodFactory.createGetMethod("url/1.xml")).thenReturn(
				mockGetMethod);

		restClient.setMethodFactory(mockMethodFactory);
		restClient.setHttpClientAdapter(mockHttpClientAdapter);

		try {
			when(mockGetMethod.getResponseBody()).thenReturn(
					new String(
							"<resource><id type=\"integer\">1</id></resource>")
							.getBytes());

			when(mockHttpClientAdapter.executeMethod(mockGetMethod))
					.thenReturn(HttpStatus.SC_OK);
			ResourceImpl resourceImpl = (ResourceImpl) restClient.getById(1,
					ResourceImpl.class);
			assertEquals(1, resourceImpl.getId());

			verify(mockHttpClientAdapter).executeMethod(mockGetMethod);
			verify(mockGetMethod).getResponseBody();
			verify(mockGetMethod).releaseConnection();
			verify(mockMethodFactory).createGetMethod("url/1.xml");
		} catch (Exception e) {
			fail("not expected");
		}

	}

	@Test
	public void testShouldSaveResource() {
		RestClientCore restClient = restClient(URL, "username", "password");
		// set up for writing

		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		PostMethod mockPostMethod = mock(PostMethod.class);
		MethodFactory mockMethodFactory = mock(MethodFactory.class);
		when(mockMethodFactory.createPostMethod("url.xml")).thenReturn(
				mockPostMethod);

		restClient.setMethodFactory(mockMethodFactory);
		restClient.setHttpClientAdapter(mockHttpClientAdapter);

		try {

			when(mockHttpClientAdapter.executeMethod(mockPostMethod))
					.thenReturn(HttpStatus.SC_CREATED);

			Resource resource = new ResourceImpl();
			restClient.save(resource);

			verify(mockHttpClientAdapter).executeMethod(mockPostMethod);
			verify(mockPostMethod).addParameters(ResourceUtil.getNameValuePairs(resource));
			verify(mockPostMethod).releaseConnection();
			verify(mockMethodFactory).createPostMethod("url.xml");
		} catch (Exception e) {
			fail("not expected");
		}

	}

	@Test
	public void testShouldUpdateResource() {
		RestClientCore restClient = restClient(URL, "username", "password");
		// set up for writing

		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		PutMethod mockPutMethod = mock(PutMethod.class);
		MethodFactory mockMethodFactory = mock(MethodFactory.class);
		when(mockMethodFactory.createPutMethod("url/1.xml")).thenReturn(
				mockPutMethod);

		restClient.setMethodFactory(mockMethodFactory);
		restClient.setHttpClientAdapter(mockHttpClientAdapter);

		try {
			when(mockHttpClientAdapter.executeMethod(mockPutMethod))
					.thenReturn(HttpStatus.SC_ACCEPTED);

			Resource resource = new ResourceImpl();
			resource.setId(1);
			restClient.update(resource);

			verify(mockHttpClientAdapter).executeMethod(mockPutMethod);
			verify(mockPutMethod).setQueryString(ResourceUtil.getNameValuePairs(resource));
			verify(mockPutMethod).releaseConnection();
			verify(mockMethodFactory).createPutMethod("url/1.xml");
		} catch (Exception e) {
			fail("not expected" + e.getMessage());
		}

	}

	@Test
	public void testShouldDeleteResource() {
		RestClientCore restClient = restClient(URL, "username", "password");
		// set up for writing

		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		DeleteMethod mockDeleteMethod = mock(DeleteMethod.class);
		MethodFactory mockMethodFactory = mock(MethodFactory.class);
		when(mockMethodFactory.createDeleteMethod("url/1.xml")).thenReturn(
				mockDeleteMethod);

		restClient.setMethodFactory(mockMethodFactory);
		restClient.setHttpClientAdapter(mockHttpClientAdapter);

		try {

			when(mockHttpClientAdapter.executeMethod(mockDeleteMethod))
					.thenReturn(HttpStatus.SC_OK);

			Resource resource = new ResourceImpl();
			resource.setId(1);
			restClient.delete(resource);

			verify(mockHttpClientAdapter).executeMethod(mockDeleteMethod);
			verify(mockDeleteMethod).releaseConnection();
			verify(mockMethodFactory).createDeleteMethod("url/1.xml");
		} catch (Exception e) {
			fail("not expected");
		}

	}

	private RestClientCore restClient(String url, String username,
			String password) {
		RestClientCore restClient = new RestClientCore(URL);
		return restClient;
	}

}
