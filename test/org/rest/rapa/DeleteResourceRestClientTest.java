package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class DeleteResourceRestClientTest {
	@Test
	public void shouldDeleteResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl(1);
		FormatHandler handler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		RestClient client = new RestClient(resourceUrl,handler,httpMethodExecutor);
		assertThatRestClientDoesnotThrowExceptionOnDelete(resource, client);
	}
	
	@Test
	public void shouldFailToDeleteIfUnableToHttpDeleteResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl(1);
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		doThrow(new IOException()).when(httpMethodExecutor).delete("http://test.com/1");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientThrowsExceptionOnDelete(resource, client);
	}
	
	
	private void assertThatRestClientDoesnotThrowExceptionOnDelete(
			Resource resource, RestClient client) {
		try{
			client.delete(resource);
		}catch (RestClientException e) {
			fail("Not supposed to throw a RestClientException");
		}
	}	
	private void assertThatRestClientThrowsExceptionOnDelete(Resource resource,
			RestClient client) {
		try{
			client.delete(resource);
			fail("Supposed to throw a RestClientException");
		}catch (RestClientException e) {
		}
	}	
}
