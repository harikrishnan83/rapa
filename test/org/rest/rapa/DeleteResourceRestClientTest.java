package org.rest.rapa;

import static org.mockito.Mockito.doThrow;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class DeleteResourceRestClientTest extends AbstractHttpMethodTest {

	@Before
	public void before() {
		super.before();
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
