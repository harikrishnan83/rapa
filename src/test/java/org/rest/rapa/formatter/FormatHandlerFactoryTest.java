package org.rest.rapa.formatter;

import static org.junit.Assert.*;
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
	public void shouldReturnXMLHandlerWhenPassedXMLAsFormat() {
		assertEquals(XMLHandler.class, formatHandlerFactory.create(Formats.XML)
				.getClass());
	}

	@Test
	public void shouldReturnJSONHandlerWhenPassedJSONAsFormat() {
		assertEquals(JSonHandler.class, formatHandlerFactory.create(
				Formats.JSON).getClass());
	}

	@Test
	public void shouldReturnNullWhenAnyOtherFormat() {
		assertNull(formatHandlerFactory.create(null));
	}
}
