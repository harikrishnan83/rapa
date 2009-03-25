package org.rest.rapa;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;
import org.rest.rapa.formatter.XMLHandler;


public class FormatHandlerFactoryTest {
	
	@Test
	public void testShouldReturnXMLHandlerWhenPassedXMLAsFormat() {
		FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();
		Assert.assertEquals(new XMLHandler().getClass(), formatHandlerFactory.create("xml").getClass());
	}
	
	@Test
	public void testShouldThrowUnsupportedFormatExceptionWhenPassedUnsupportedFormat() {
		FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();
		try {
			formatHandlerFactory.create("unknown");
			fail("UnsuppportFormatException was not thrown");
		} catch (Exception e) {
			assertEquals("Unsupported Format unknown", e.getMessage());
		}
	}

}
