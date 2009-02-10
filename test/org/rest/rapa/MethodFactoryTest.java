package org.rest.rapa;

import static org.junit.Assert.*;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

public class MethodFactoryTest{

	@Test
	public void shouldReturnGetMethod() {
		MethodFactory methodFactory = new MethodFactoryImpl();
		GetMethod getMethod = methodFactory.createGetMethod("Hi");
		try {
			assertTrue("Hi".equals(getMethod.getURI().toString()));
		} catch (URIException e) {
			fail();
		}
	}

}
