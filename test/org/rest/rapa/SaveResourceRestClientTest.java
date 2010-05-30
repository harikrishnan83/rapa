package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.rest.rapa.resource.Resource;

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
	
	@Test
	public void shouldSetIdOnSuccessfulSave() throws Exception {
		TestResource testResource = new TestResource("HoldenVCaulfield");
		when(formatHandler.serialize(testResource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		when(httpMethodExecutor.post("<test>1</test>", "http://test.com","xml")).thenReturn("response");
		when(formatHandler.deserialize("response", TestResource.class)).thenReturn(new TestResource("name", 1));

		assertEquals(0, testResource.getId());
		
		client.save(testResource);
		
		assertEquals(1, testResource.getId());
	}
}

class TestResource implements Resource {

	private final String name;
	private int id;

	public TestResource(String name) {
		this.name = name;
	}

	public TestResource(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
}
