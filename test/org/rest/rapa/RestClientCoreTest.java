package org.rest.rapa;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXB;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.junit.Test;
import org.rest.rapa.HttpClientAdapter;
import org.rest.rapa.MethodFactory;
import org.rest.rapa.RestClientCore;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RestClientCoreTest {

	private static final String URL = "url";

	private String marshall(Resource resource) {
		OutputStream outputStream = new ByteArrayOutputStream();
		JAXB.marshal(resource, outputStream);
		String xml = outputStream.toString();
		return xml;
	}

	/*
	 * @Test(expected = IllegalArgumentException.class) public void
	 * ShouldThrowIllegalArgumntsExceptionIfAnEmptyStringIsPassed() { new
	 * RestClientCore(""); fail("hmm...RestClientCore is accepting an empty
	 * string..this isnt the way it should work!!"); }
	 */

	@Test
	public void shouldGetResourceById() {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		MethodFactory mockMethodFactory = mock(MethodFactory.class);

		GetMethod mockGetMethod = mock(GetMethod.class);
		when(mockMethodFactory.createGetMethod("url/1.xml")).thenReturn(
				mockGetMethod);

		FormatHandler mockFormatHandler = mock(FormatHandler.class);

		Resource resource = new ResourceImpl();
		resource.setId(1);

		when(
				mockFormatHandler.decode(
						"<resource><id type=\"integer\">1</id></resource>",
						ResourceImpl.class)).thenReturn(resource);

		RestClientCore restClient = new RestClientCore(URL,
				mockHttpClientAdapter, mockMethodFactory, mockFormatHandler);

		when(mockFormatHandler.getExtension()).thenReturn("xml");

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
			e.printStackTrace();
			fail("not expected");
		}

	}

	@Test
	public void shouldSaveResource() {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		MethodFactory mockMethodFactory = mock(MethodFactory.class);

		PostMethod mockPostMethod = mock(PostMethod.class);
		when(mockMethodFactory.createPostMethod("url.xml")).thenReturn(
				mockPostMethod);

		FormatHandler mockFormatHandler = mock(FormatHandler.class);
		RestClientCore restClient = new RestClientCore(URL,
				mockHttpClientAdapter, mockMethodFactory, mockFormatHandler);
		try {

			when(mockHttpClientAdapter.executeMethod(mockPostMethod))
					.thenReturn(HttpStatus.SC_CREATED);

			Resource resource = new ResourceImpl();
			when(mockFormatHandler.encode(resource)).thenReturn(
					marshall(resource));
			when(mockFormatHandler.getExtension()).thenReturn("xml");
			restClient.save(resource);

			verify(mockHttpClientAdapter).executeMethod(mockPostMethod);
			verify(mockPostMethod).setRequestHeader("Content-type", "text/xml");
			verify(mockPostMethod).setRequestBody(marshall(resource));
			verify(mockPostMethod).releaseConnection();
			verify(mockMethodFactory).createPostMethod("url.xml");
		} catch (Exception e) {
			fail("not expected");
		}

	}

	@Test
	public void shouldUpdateResource() {
		MethodFactory mockMethodFactory = mock(MethodFactory.class);
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);

		PutMethod mockPutMethod = mock(PutMethod.class);
		when(mockMethodFactory.createPutMethod("url/1.xml")).thenReturn(
				mockPutMethod);

		FormatHandler mockFormatHandler = mock(FormatHandler.class);
		RestClientCore restClient = new RestClientCore(URL,
				mockHttpClientAdapter, mockMethodFactory, mockFormatHandler);
		try {
			when(mockHttpClientAdapter.executeMethod(mockPutMethod))
					.thenReturn(HttpStatus.SC_ACCEPTED);

			Resource resource = new ResourceImpl();
			resource.setId(1);
			when(mockFormatHandler.encode(resource)).thenReturn(
					marshall(resource));
			when(mockFormatHandler.getExtension()).thenReturn("xml");
			restClient.update(resource);

			verify(mockHttpClientAdapter).executeMethod(mockPutMethod);
			verify(mockPutMethod).setRequestHeader("Content-type", "text/xml");
			verify(mockPutMethod).setRequestBody(marshall(resource));
			verify(mockPutMethod).releaseConnection();
			verify(mockMethodFactory).createPutMethod("url/1.xml");
		} catch (Exception e) {
			fail("not expected" + e.getMessage());
		}

	}

	@Test
	public void shouldDeleteResource() {
		HttpClientAdapter mockHttpClientAdapter = mock(HttpClientAdapter.class);
		MethodFactory mockMethodFactory = mock(MethodFactory.class);

		DeleteMethod mockDeleteMethod = mock(DeleteMethod.class);
		when(mockMethodFactory.createDeleteMethod("url/1.xml")).thenReturn(
				mockDeleteMethod);

		FormatHandler mockFormatHandler = mock(FormatHandler.class);

		when(mockFormatHandler.getExtension()).thenReturn("xml");
		RestClientCore restClient = new RestClientCore(URL,
				mockHttpClientAdapter, mockMethodFactory, mockFormatHandler);
		try {

			when(mockHttpClientAdapter.executeMethod(mockDeleteMethod))
					.thenReturn(HttpStatus.SC_OK);

			ResourceImpl resource = new ResourceImpl();
			resource.setId(1);
			restClient.delete(resource);

			verify(mockHttpClientAdapter).executeMethod(mockDeleteMethod);
			verify(mockDeleteMethod).releaseConnection();
			verify(mockMethodFactory).createDeleteMethod("url/1.xml");
		} catch (Exception e) {
			fail("not expected");
		}

	}

}
