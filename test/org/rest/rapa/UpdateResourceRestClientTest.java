package org.rest.rapa;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class UpdateResourceRestClientTest extends AbstractHttpMethodTest {
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldUpdateResource() throws Exception {
		client.update(resource);
	}

	@Test
	public void shouldSerializeResourceBeforeUpdating() throws Exception {
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		client.update(resource);
		verify(formatHandler).serialize(resource);
	}

	@Test
	public void shouldPostSerializedResource() throws Exception {
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		client.update(resource);
		verify(httpMethodExecutor).put("<test>1</test>", "http://test.com/1",
				"xml");
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToUpdateIfUnableToSerializeResource()
			throws Exception {
		doThrow(new Exception()).when(formatHandler).serialize(resource);
		client.update(resource);
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToUpdateIfUnableToHttpPostResource() throws Exception {
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		doThrow(new IOException()).when(httpMethodExecutor).put(
				"<test>1</test>", "http://test.com/1", "xml");
		client.update(resource);
	}
}
