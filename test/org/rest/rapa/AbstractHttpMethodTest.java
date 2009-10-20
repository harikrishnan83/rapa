package org.rest.rapa;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public abstract class AbstractHttpMethodTest {
	protected Url					resourceUrl;
	protected Resource			resource;
	protected FormatHandler		formatHandler;
	protected HttpMethodExecutor	httpMethodExecutor;
	protected RestClient			client;

	@Before
	public void before() {
		resourceUrl = new Url("http://test.com", "xml", false);
		resource = new ResourceImpl(1);
		formatHandler = mock(FormatHandler.class);
		httpMethodExecutor = mock(HttpMethodExecutor.class);
		client = new RestClient(resourceUrl, formatHandler, httpMethodExecutor);
	}
}
