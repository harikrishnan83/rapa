package org.rest.rapa.formatter.xstream;

import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;

import com.thoughtworks.xstream.XStream;

public class XMLHandler implements FormatHandler {
	
	private XStream xStream;

	public XMLHandler(XStream xStream) {
		this.xStream = xStream;
	}

	@Override
	public Resource deserialize(String content, Class<?> resourceType)
			throws Exception {
		return (Resource) xStream.fromXML(content, resourceType.newInstance());
	}

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public String getExtension() {
		return "xml";
	}

	@Override
	public String serialize(Resource resource) throws Exception {
		return xStream.toXML(resource);
	}

}
