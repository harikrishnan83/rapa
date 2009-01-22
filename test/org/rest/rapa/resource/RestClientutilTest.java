package org.rest.rapa.resource;

import static org.junit.Assert.*;

import org.apache.commons.httpclient.NameValuePair;
import org.junit.Test;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;
import org.rest.rapa.resource.ResourceUtil;

import junit.framework.TestCase;

public class RestClientutilTest extends TestCase {

	@Test
	public void testShouldReturnNameValuePair() {
		Resource resource = new ResourceImpl();
		resource.setId(1);
		NameValuePair[] nameValuePairs = {new NameValuePair("resourceimpl[id]", "1")};
		try {
			assertEquals(nameValuePairs[0], ResourceUtil.getNameValuePairs(resource)[0]);
		} catch (Exception e) {
			fail("not expected. Cause: " + e.getMessage());
		}
	}

}
