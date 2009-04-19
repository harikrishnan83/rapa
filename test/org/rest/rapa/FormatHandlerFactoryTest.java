package org.rest.rapa;

import static org.junit.Assert.*;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.rest.rapa.formatter.JSonHandler;
import org.rest.rapa.formatter.XMLHandler;

public class FormatHandlerFactoryTest extends TestCase {

	@Test
	public void testShouldReturnXMLHandlerWhenPassedXMLAsFormat() {
		FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();
		Assert.assertEquals(new XMLHandler().getClass(), formatHandlerFactory
				.create("xml").getClass());
	}
	
	@Test
	public void testShouldReturnJSONHandlerWhenPassedJSONAsFormat() {
		FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();
		Assert.assertEquals(new JSonHandler().getClass(), formatHandlerFactory
				.create("json").getClass());
	}
	
	@Test
	public void testShouldIgnoreCaseWhenComparingStrings() {
		FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();
		Assert.assertEquals(new XMLHandler().getClass(), formatHandlerFactory
				.create("XML").getClass());
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
