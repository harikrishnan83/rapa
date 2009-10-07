package org.rest.rapa;

import static org.junit.Assert.*;
import org.junit.Test;
import org.apache.commons.httpclient.methods.*;

public class HttpMethodProviderTest {
	@Test
	public void shouldProvideGetMethod(){
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(GetMethod.class, provider.getMethod().getClass());
	}
	@Test
	public void shouldProvidePostMethod(){
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(PostMethod.class, provider.postMethod().getClass());
	}
	@Test
	public void shouldProvidePutMethod(){
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(PutMethod.class, provider.putMethod().getClass());
	}
	@Test
	public void shouldProvideDeleteMethod(){
		HttpMethodProvider provider = new HttpMethodProvider();
		assertEquals(DeleteMethod.class, provider.deleteMethod().getClass());
	}

}
