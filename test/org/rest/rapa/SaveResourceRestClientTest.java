package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class SaveResourceRestClientTest extends AbstractHttpMethodTest {
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldSaveResource() throws Exception {
		client.save(resource);
	}

	@Test
	public void shouldSerializeResourceBeforePosting() throws Exception {
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		client.save(resource);
		verify(formatHandler).serialize(resource);
	}

	@Test
	public void shouldPostSerializedResource() throws Exception {
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		client.save(resource);
		verify(httpMethodExecutor).post("<test>1</test>", "http://test.com",
				"xml");
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToSaveIfUnableToSerializeResource() throws Exception {
		doThrow(new Exception()).when(formatHandler).serialize(resource);
		client.save(resource);
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToSaveIfUnableToHttpPostResource() throws Exception {
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		doThrow(new IOException()).when(httpMethodExecutor).post(
				"<test>1</test>", "http://test.com", "xml");
		client.save(resource);
	}
}
