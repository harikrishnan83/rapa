package org.rest.rapa.formatter;

import org.rest.rapa.resource.Resource;
import org.rest.rapa.resource.ResourceImpl;

public class NullFormatHandler implements FormatHandler{

	public Resource deserialize(String content, Class resourceType) {
		return new ResourceImpl();
	}

	public String getContentType() {
		return "";
	}

	public String getExtension() {
		return "";
	}

	public String serialize(Resource resource) {
		return "";
	}

}
