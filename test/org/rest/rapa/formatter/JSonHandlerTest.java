package org.rest.rapa.formatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import junit.framework.TestCase;

import org.junit.Test;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class JSonHandlerTest extends TestCase {

	@Test
	public void testIfTheJSonContentIsDecodedToAppropriateResourceObject() {
		try {
			Resource resourceImpl = new ResourceImpl();
			resourceImpl.setId(1);
			JSonHandler handler = new JSonHandler();
			String jsonContent = handler.serialize(resourceImpl);
			Resource decodedResource = handler.deserialize(jsonContent,
					ResourceImpl.class);
			assertTrue(decodedResource instanceof ResourceImpl);
			assertEquals(1, ((ResourceImpl) decodedResource).getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail("unexpected exception!");
		}
	}

	@Test
	public void testIfTheResourceObjectIsEncodedToAppropriateJSonFormat() {
		try {
			Resource resourceImpl = new ResourceImpl();
			resourceImpl.setId(1);
			JSonHandler handler = new JSonHandler();
			String jsonContent = handler.serialize(resourceImpl);
			Resource decodedResource = handler.deserialize(jsonContent,
					ResourceImpl.class);
			assertNotNull(decodedResource);
			assertEquals(resourceImpl.getId(), decodedResource.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail("unexpected exception!");
		}
	}
}
