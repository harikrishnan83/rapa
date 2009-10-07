package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class SaveResourceRestClientTest {
	@Test
	public void shouldSaveResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl();
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientDoesnotThrowExceptionOnSave(resource, client);
	}
	
	@Test
	public void shouldSerializeResourceBeforePosting() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl();
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		client.save(resource);
		verify(formatHandler).serialize(resource);
	}	
	
	@Test
	public void shouldPostSerializedResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl();
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		client.save(resource);
		verify(httpMethodExecutor).post("<test>1</test>", "http://test.com", "xml");
	}	
	
	@Test
	public void shouldFailToSaveIfUnableToSerializeResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl();
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		doThrow(new Exception()).when(formatHandler).serialize(resource);
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientThrowsExceptionOnSave(resource, client);
	}

	@Test
	public void shouldFailToSaveIfUnableToHttpPostResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl();
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		doThrow(new IOException()).when(httpMethodExecutor).post("<test>1</test>", "http://test.com", "xml");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientThrowsExceptionOnSave(resource, client);
	}
	
	private void assertThatRestClientThrowsExceptionOnSave(Resource resource,
			RestClient client) {
		try{
			client.save(resource);
			fail("Supposed to throw a RestClientException");
		}catch (RestClientException e) {
		}
	}
	
	private void assertThatRestClientDoesnotThrowExceptionOnSave(
			Resource resource, RestClient client) {
		try{
			client.save(resource);
		}catch (RestClientException e) {
			fail("Not supposed to throw a RestClientException");
		}
	}	
}
