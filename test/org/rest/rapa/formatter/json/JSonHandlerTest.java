package org.rest.rapa.formatter.json;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import org.json.me.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class JSonHandlerTest {

	private Resource resourceImpl;
	private JSonHandler handler;

	@Before
	public void setup() {
		resourceImpl = new ResourceImpl();
		resourceImpl.setId(1);
		handler = new JSonHandler();
		
	}
	
	@Test
	public void testIfTheJSonContentIsDecodedToAppropriateResourceObject() throws JSONException {
			String jsonContent = handler.serialize(resourceImpl);
			Resource deserialisedResource = handler.deserialize(jsonContent,
					ResourceImpl.class);
			assertTrue(deserialisedResource instanceof ResourceImpl);
			assertEquals(1, deserialisedResource.getId());
		
	}

	@Test
	public void testIfTheResourceObjectIsEncodedToAppropriateJSonFormat() throws JSONException {
			String jsonContent = handler.serialize(resourceImpl);
			Resource deserialisedResource = handler.deserialize(jsonContent,
					ResourceImpl.class);
			assertNotNull(deserialisedResource);
			assertEquals(resourceImpl.getId(), deserialisedResource.getId());
	}
	
	@Test
	public void testShouldReturnXMLAsExtension() {
		assertEquals("json", handler.getExtension());
	}
	
	@Test
	public void testShouldReturnContentType() {
		assertEquals("text/json", handler.getContentType());
	}

}
