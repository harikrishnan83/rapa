package org.rest.rapa.formatter;

import org.rest.rapa.resource.Resource;

public interface FormatHandler {
	
	String encode(Resource resource) throws Exception;
	Resource decode(String content, Class resourceType) throws Exception;
	String getExtension();

}
