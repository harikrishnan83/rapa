package org.rest.rapa;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.rest.rapa.resource.ResourceImpl;

public class GetResourceRestClientTest extends AbstractHttpMethodTest {
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldGetRequestedResource() throws Exception {
		when(httpMethodExecutor.get("http://test.com/1", emptyRequestHeaders)).thenReturn(
				"<test>1</test>");
		when(formatHandler.deserialize("<test>1</test>", ResourceImpl.class))
				.thenReturn(resource);
		client.getById(1, ResourceImpl.class);
	}

	@Test
	public void shouldHttpGetResource() throws Exception {
		client.getById(1, ResourceImpl.class);
		verify(httpMethodExecutor).get("http://test.com/1", emptyRequestHeaders);
	}

	@Test
	public void shouldDeserializeResource() throws Exception {
		when(httpMethodExecutor.get("http://test.com/1", emptyRequestHeaders)).thenReturn(
				"<test>1</test>");
		client.getById(1, ResourceImpl.class);
		verify(formatHandler).deserialize("<test>1</test>", ResourceImpl.class);
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToGetResourceIfUnableToDeSerializeTheResource()
			throws Exception {
		when(httpMethodExecutor.get("http://test.com/1", emptyRequestHeaders)).thenReturn(
				"<test>1</test>");
		doThrow(new Exception()).when(formatHandler).deserialize(
				"<test>1</test>", ResourceImpl.class);
		client.getById(1, ResourceImpl.class);
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToGetIfUnableToHttpGetRemoteResource()
			throws Exception {
		doThrow(new IOException()).when(httpMethodExecutor).get(
				"http://test.com/1", emptyRequestHeaders);
		client.getById(1, ResourceImpl.class);
	}
}
