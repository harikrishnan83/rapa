package org.rest.rapa.formatter;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandlerFactory;
import org.rest.rapa.formatter.Formats;
import org.rest.rapa.formatter.JSonHandler;
import org.rest.rapa.formatter.XMLHandler;

public class FormatHandlerFactoryTest {

	private FormatHandlerFactory formatHandlerFactory;

	@Before
	public void setup() {
		formatHandlerFactory = new FormatHandlerFactory();
	}
	
	@Test
	public void testShouldReturnXMLHandlerWhenPassedXMLAsFormat() {
		Assert.assertEquals(XMLHandler.class, formatHandlerFactory
				.create(Formats.XML).getClass());
	}
	
	@Test
	public void testShouldReturnJSONHandlerWhenPassedJSONAsFormat() {
		Assert.assertEquals(JSonHandler.class, formatHandlerFactory
				.create(Formats.JSON).getClass());
	}
	
	@Test
	public void testShouldIgnoreCaseWhenComparingStrings() {
		Assert.assertEquals(XMLHandler.class, formatHandlerFactory
				.create(Formats.XML).getClass());
	}
}
