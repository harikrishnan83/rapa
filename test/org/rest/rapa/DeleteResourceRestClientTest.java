package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class DeleteResourceRestClientTest {

	private Url					resourceUrl;
	private Resource			resource;
	private FormatHandler		handler;
	private HttpMethodExecutor	httpMethodExecutor;
	private RestClient			client;

	@Before
	public void before() {
		resourceUrl = new Url("http://test.com", "xml", false);
		resource = new ResourceImpl(1);
		handler = mock(FormatHandler.class);
		httpMethodExecutor = mock(HttpMethodExecutor.class);
		client = new RestClient(resourceUrl, handler, httpMethodExecutor);
	}

	public void shouldDeleteResource() throws Exception {
		client.delete(resource);
	}

	@Test(expected = RestClientException.class)
	public void shouldFailToDeleteIfUnableToHttpDeleteResource()
			throws Exception {
		doThrow(new IOException()).when(httpMethodExecutor).delete(
				"http://test.com/1");
		client.delete(resource);
	}
}
