package org.rest.rapa.formatter;

import org.rest.rapa.resource.Resource;

public class JSonHandler implements FormatHandler {

	@Override
	public Resource decode(String content, Class resourceType) {
		return null;
	}

	@Override
	public String encode(Resource resource) {
		return null;
	}

	@Override
	public String getExtension() {
		return "json";
	}
}
