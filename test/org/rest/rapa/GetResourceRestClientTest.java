package org.rest.rapa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class GetResourceRestClientTest {
	@Test
	public void shouldGetRequestedResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		Resource resource = new ResourceImpl();
		FormatHandler handler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(httpMethodExecutor.get("http://test.com/1")).thenReturn("<test>1</test>");
		when(handler.deserialize("<test>1</test>", ResourceImpl.class)).thenReturn(resource);
		RestClient client = new RestClient(resourceUrl,handler,httpMethodExecutor);
		assertThatRestClientDoesnotThrowExceptionOnGet(client);
	}

	@Test
	public void shouldHttpGetResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		FormatHandler handler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		RestClient client = new RestClient(resourceUrl,handler,httpMethodExecutor);
		client.getById(1, ResourceImpl.class);		
		verify(httpMethodExecutor).get("http://test.com/1");
	}

	@Test
	public void shouldDeserializeResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		FormatHandler handler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(httpMethodExecutor.get("http://test.com/1")).thenReturn("<test>1</test>");		
		RestClient client = new RestClient(resourceUrl,handler,httpMethodExecutor);
		client.getById(1, ResourceImpl.class);
		verify(handler).deserialize("<test>1</test>", ResourceImpl.class);
	}
	
	@Test
	public void shouldFailToGetResourceIfUnableToDeSerializeTheResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		when(httpMethodExecutor.get("http://test.com/1")).thenReturn("<test>1</test>");		
		doThrow(new Exception()).when(formatHandler).deserialize("<test>1</test>", ResourceImpl.class);
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientThrowsExceptionOnGet(client);
	}

	@Test
	public void shouldFailToGetIfUnableToHttpGetRemoteResource() throws Exception{
		Url resourceUrl = new Url("http://test.com", "xml", false);
		FormatHandler formatHandler = mock(FormatHandler.class);
		HttpMethodExecutor httpMethodExecutor = mock(HttpMethodExecutor.class);
		doThrow(new IOException()).when(httpMethodExecutor).get("http://test.com/1");
		RestClient client = new RestClient(resourceUrl,formatHandler,httpMethodExecutor);
		assertThatRestClientThrowsExceptionOnGet(client);
	}
	
	
	private void assertThatRestClientDoesnotThrowExceptionOnGet(
			RestClient client) {
		try{
			client.getById(1, ResourceImpl.class);
		}catch (RestClientException e) {
			fail("Not supposed to throw a RestClientException");
		}
	}
	
	private void assertThatRestClientThrowsExceptionOnGet(RestClient client) {
		try{
			client.getById(1, ResourceImpl.class);
			fail("Supposed to throw a RestClientException");
		}catch (RestClientException e) {
		}
	}	
}
