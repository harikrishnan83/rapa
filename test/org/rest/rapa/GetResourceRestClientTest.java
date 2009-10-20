package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class GetResourceRestClientTest extends AbstractHttpMethodTest {
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldGetRequestedResource() throws Exception {
		when(httpMethodExecutor.get("http://test.com/1")).thenReturn(
				"<test>1</test>");
		when(handler.deserialize("<test>1</test>", ResourceImpl.class))
				.thenReturn(resource);
		client.getById(1, ResourceImpl.class);
	}

	@Test
	public void shouldHttpGetResource() throws Exception {
		client.getById(1, ResourceImpl.class);
		verify(httpMethodExecutor).get("http://test.com/1");
	}

	@Test
	public void shouldDeserializeResource() throws Exception {
		when(httpMethodExecutor.get("http://test.com/1")).thenReturn(
				"<test>1</test>");
		client.getById(1, ResourceImpl.class);
		verify(handler).deserialize("<test>1</test>", ResourceImpl.class);
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToGetResourceIfUnableToDeSerializeTheResource()
			throws Exception {
		when(httpMethodExecutor.get("http://test.com/1")).thenReturn(
				"<test>1</test>");
		doThrow(new Exception()).when(handler).deserialize("<test>1</test>",
				ResourceImpl.class);
		client.getById(1, ResourceImpl.class);
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToGetIfUnableToHttpGetRemoteResource()
			throws Exception {
		doThrow(new IOException()).when(httpMethodExecutor).get(
				"http://test.com/1");
		client.getById(1, ResourceImpl.class);
	}
}
