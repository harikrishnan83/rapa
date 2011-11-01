package org.rest.rapa.formatter.json;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.asm.tree.TryCatchBlockNode;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	public void JSonContentShouldBeDecodedToAppropriateResourceObject() {
		String jsonContent = handler.serialize(resourceImpl);
		Resource deserialisedResource = handler.deserialize(jsonContent,
				ResourceImpl.class);
		assertTrue(deserialisedResource instanceof ResourceImpl);
		assertEquals(1, deserialisedResource.getId());

	}

	@Test
	public void resourceObjectShouldBeEncodedToAppropriateJSonFormat() {
		String jsonContent = handler.serialize(resourceImpl);
		Resource deserialisedResource = handler.deserialize(jsonContent,
				ResourceImpl.class);
		assertNotNull(deserialisedResource);
		assertEquals(resourceImpl.getId(), deserialisedResource.getId());
	}

	@Test
	public void shouldReturnJsonAsExtension() {
		assertEquals("json", handler.getExtension());
	}

	@Test
	public void shouldReturnContentType() {
		assertEquals("text/json", handler.getContentType());
	}

}
