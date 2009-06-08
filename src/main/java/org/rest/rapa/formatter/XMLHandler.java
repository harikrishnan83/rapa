package org.rest.rapa.formatter;

import org.rest.rapa.resource.Resource;

import javax.xml.bind.JAXB;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

public class XMLHandler implements FormatHandler {

	public Resource deserialize(String content, Class clazz) {
		return (Resource) JAXB.unmarshal(new StringReader(content), clazz);
	}

	public String serialize(Resource resource) {
		OutputStream outputStream = new ByteArrayOutputStream();
		JAXB.marshal(resource, outputStream);
		return outputStream.toString();
	}

	public String getExtension() {
		return "xml";
	}

	public String getContentType() {
		return "text/xml";
	}

}
