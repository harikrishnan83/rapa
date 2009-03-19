package org.rest.rapa.formatter;

import org.rest.rapa.resource.Resource;

public interface FormatHandler {
	
	String encode(Resource resource);
	Resource decode(String content, Class resourceType);
	String getExtension();

}
