package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class UpdateResourceRestClientTest {
	@Test
	public void shouldUpdateResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl(1);
		FormatHandler handler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		RestClient client = new RestClient(resourceUrl,handler,httpMethodExecutor);
		assertThatRestClientDoesnotThrowExceptionOnUpdate(resource, client);
	}

	@Test
	public void shouldSerializeResourceBeforeUpdating() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl(1);
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		client.update(resource);
		verify(formatHandler).serialize(resource);
	}	
	
	@Test
	public void shouldPostSerializedResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl(1);
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		client.update(resource);
		verify(httpMethodExecutor).put("<test>1</test>", "http://test.com/1", "xml");
	}	
	
	@Test
	public void shouldFailToUpdateIfUnableToSerializeResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl(1);
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		doThrow(new Exception()).when(formatHandler).serialize(resource);
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientThrowsExceptionOnUpdate(resource, client);
	}
	
	@Test
	public void shouldFailToUpdateIfUnableToHttpPostResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl(1);
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(formatHandler.serialize(resource)).thenReturn("<test>1</test>");
		when(formatHandler.getContentType()).thenReturn("xml");
		doThrow(new IOException()).when(httpMethodExecutor).put("<test>1</test>", "http://test.com/1", "xml");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientThrowsExceptionOnUpdate(resource, client);
	}	

	
	private void assertThatRestClientThrowsExceptionOnUpdate(Resource resource,
			RestClient client) {
		try{
			client.update(resource);
			fail("Supposed to throw a RestClientException");
		}catch (RestClientException e) {
		}
	}
	private void assertThatRestClientDoesnotThrowExceptionOnUpdate(
			Resource resource, RestClient client) {
		try{
			client.update(resource);
		}catch (RestClientException e) {
			fail("Not supposed to throw a RestClientException");
		}
	}	
}

