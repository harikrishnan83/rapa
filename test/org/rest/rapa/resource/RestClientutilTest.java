package org.rest.rapa.resource;

import static org.junit.Assert.*;
import org.apache.commons.httpclient.NameValuePair;
import org.junit.Test;

public class RestClientutilTest {

	@Test
	public void shouldReturnNameValuePair() {
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
