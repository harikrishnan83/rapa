package org.rest.rapa;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;
import org.rest.rapa.MethodFactory;
import org.rest.rapa.MethodFactoryImpl;

public class MethodFactoryTest extends TestCase {

	@Test
	public void testShouldReturnGetMethod() {
		MethodFactory methodFactory = new MethodFactoryImpl();
		GetMethod getMethod = methodFactory.createGetMethod("Hi");
		try {
			assertTrue("Hi".equals(getMethod.getURI().toString()));
		} catch (URIException e) {
			fail();
		}
	}

}
