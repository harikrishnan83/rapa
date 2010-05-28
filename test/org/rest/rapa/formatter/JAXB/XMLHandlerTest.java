package org.rest.rapa.formatter.JAXB;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.Test;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

import javax.xml.bind.JAXB;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


public class XMLHandlerTest {
	
	@Test
	public void shouldReturnXMLAsExtension() {
		assertEquals("xml", getXMLHandler().getExtension());
	}
	
	@Test
	public void shouldReturnContentType() {
		assertEquals("text/xml", getXMLHandler().getContentType());
	}
	
	@Test
	public void shouldSerializeSingleResource() {
		Resource resource = new ResourceImpl();
		resource.setId(1);
		try {
			assertEquals(marshall(resource), getXMLHandler().serialize(resource));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void shouldDeserializeSingleResource() {
		Resource resource = new ResourceImpl();
		resource.setId(1);
		try {
			assertEquals(1, (getXMLHandler().deserialize(marshall(resource), ResourceImpl.class)).getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private String marshall(Resource resource) {
		OutputStream outputStream = new ByteArrayOutputStream();
		JAXB.marshal(resource, outputStream);
        return outputStream.toString();
	}

	private FormatHandler getXMLHandler() {
        return new XMLHandler();
	}

}
