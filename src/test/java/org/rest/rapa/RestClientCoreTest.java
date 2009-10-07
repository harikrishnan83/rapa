package org.rest.rapa;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.ResourceImpl;

public class RestClientCoreTest {

	private Url mockUrl;
	private FormatHandler mockFormatHandler;
	private HttpMethodExecutor mockHttpMethodExecutor;
	private RestClientCore restClient;

	@Before
	public void setup() {
		mockFormatHandler = mock(FormatHandler.class);
		mockHttpMethodExecutor = mock(HttpMethodExecutor.class);
		mockUrl = mock(Url.class);

		restClient = new RestClientCore(mockUrl, mockFormatHandler,
				mockHttpMethodExecutor);
	}

	@Test
	public void testShouldGetResourceById() throws Exception {

		restClient.getById(1, ResourceImpl.class);

		verify(mockUrl).getResourceSpecificURL(1);
		verify(mockHttpMethodExecutor).get(anyString());
		verify(mockFormatHandler).deserialize(anyString(),
				eq(ResourceImpl.class));

	}

	@Test
	public void testShouldSaveResource() throws Exception {

		ResourceImpl resource = new ResourceImpl(1);
		restClient.save(resource);

		verify(mockUrl).getURL();
		verify(mockHttpMethodExecutor).post(anyString(), anyString(),
				anyString());
		verify(mockFormatHandler).serialize(resource);

	}

	@Test
	public void testShouldUpdateResource() throws Exception {

		ResourceImpl resource = new ResourceImpl(1);

		restClient.update(resource);

		verify(mockUrl).getResourceSpecificURL(resource.getId());
		verify(mockHttpMethodExecutor).put(anyString(), anyString(),
				anyString());
		verify(mockFormatHandler).serialize(resource);

	}

	@Test
	public void testShouldDeleteResource() throws Exception {

		ResourceImpl resource = new ResourceImpl(1);

		restClient.delete(resource);

		verify(mockUrl).getResourceSpecificURL(resource.getId());
		verify(mockHttpMethodExecutor).delete(anyString());

	}

}
