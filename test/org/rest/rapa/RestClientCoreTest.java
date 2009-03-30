package org.rest.rapa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.bind.JAXB;

import org.apache.commons.httpclient.HttpException;
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
	public void shouldGetResourceById() throws HttpException, IOException {

		FormatHandler mockFormatHandler = mock(FormatHandler.class);
		HttpMethodExecutor mockHttpMethodExecutor = mock(HttpMethodExecutor.class);
		
		when(mockHttpMethodExecutor.get(URL + "/1.xml")).thenReturn("<resource><id type=\"integer\">1</id></resource>");

		Resource resource = new ResourceImpl();
		resource.setId(1);

		try {
			when(
					mockFormatHandler.decode(
							"<resource><id type=\"integer\">1</id></resource>",
							ResourceImpl.class)).thenReturn(resource);

			RestClientCore restClient = new RestClientCore(URL,
					mockFormatHandler, mockHttpMethodExecutor);

			when(mockFormatHandler.getExtension()).thenReturn("xml");

			ResourceImpl resourceImpl = (ResourceImpl) restClient.getById(1,
					ResourceImpl.class);
			assertEquals(1, resourceImpl.getId());

			verify(mockHttpMethodExecutor).get(URL + "/1.xml");
		} catch (Exception e) {
			e.printStackTrace();
			fail("not expected");
		}

	}

	@Test
	public void shouldSaveResource() {
		
		HttpMethodExecutor mockedHttpMethodExecutor = mock(HttpMethodExecutor.class);
		FormatHandler mockFormatHandler = mock(FormatHandler.class);
		RestClientCore restClient = new RestClientCore(URL,
				mockFormatHandler, mockedHttpMethodExecutor);
		try {

			Resource resource = new ResourceImpl();
			when(mockFormatHandler.encode(resource)).thenReturn(
					marshall(resource));
			when(mockFormatHandler.getExtension()).thenReturn("xml");
			restClient.save(resource);
			
			verify(mockedHttpMethodExecutor).post(marshall(resource), URL + ".xml");

		} catch (Exception e) {
			fail("not expected");
		}

	}

	@Test
	public void shouldUpdateResource() {

		FormatHandler mockFormatHandler = mock(FormatHandler.class);
		HttpMethodExecutor mockHttpMethodExecutor = mock(HttpMethodExecutor.class);
		RestClientCore restClient = new RestClientCore(URL,
				mockFormatHandler, mockHttpMethodExecutor);
		try {

			Resource resource = new ResourceImpl();
			resource.setId(1);
			when(mockFormatHandler.encode(resource)).thenReturn(
					marshall(resource));
			when(mockFormatHandler.getExtension()).thenReturn("xml");
			restClient.update(resource);

			verify(mockHttpMethodExecutor).update(marshall(resource), URL + "/1.xml");
		} catch (Exception e) {
			fail("not expected" + e.getMessage());
		}

	}

	@Test
	public void shouldDeleteResource() {

		FormatHandler mockFormatHandler = mock(FormatHandler.class);

		when(mockFormatHandler.getExtension()).thenReturn("xml");
		HttpMethodExecutor mockHttpMethodExecutor = mock(HttpMethodExecutor.class);
		RestClientCore restClient = new RestClientCore(URL,
				mockFormatHandler, mockHttpMethodExecutor);
		try {


			ResourceImpl resource = new ResourceImpl();
			resource.setId(1);
			restClient.delete(resource);
			
			verify(mockHttpMethodExecutor).delete(URL + "/1.xml");

		} catch (Exception e) {
			fail("not expected");
		}

	}

}
