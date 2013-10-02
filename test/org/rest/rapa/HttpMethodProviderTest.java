package org.rest.rapa;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.*;

public class HttpMethodProviderTest {
	private Map<String, String> emptyRequestHeaders = new HashMap<String, String>();
	
	@Test
	public void shouldProvideGetMethod() {
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(GetMethod.class, provider.getMethod(emptyRequestHeaders).getClass());
	}
	
	@Test
	public void shouldAddRequestHeadersAndProvideGetMethod() {
		HttpMethodProvider provider = new HttpMethodProvider();
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("headerName", "headerValue");
		GetMethod getMethod = provider.getMethod(requestHeaders);
		assertEquals(GetMethod.class, getMethod.getClass());
		Header[] requestHeaders2 = getMethod.getRequestHeaders();
		assertEquals(1, requestHeaders2.length);
		Header header = requestHeaders2[0];
		assertEquals("headerName", header.getName());
		assertEquals("headerValue", header.getValue());
	}

	@Test
	public void shouldProvidePostMethod() {
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(PostMethod.class, provider.postMethod().getClass());
	}

	@Test
	public void shouldProvidePutMethod() {
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(PutMethod.class, provider.putMethod().getClass());
	}

	@Test
	public void shouldProvideDeleteMethod() {
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(DeleteMethod.class, provider.deleteMethod().getClass());
	}

}
