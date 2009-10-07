package org.rest.rapa.formatter;

import org.rest.rapa.resource.Resource;

public interface FormatHandler {

	String serialize(Resource resource) throws Exception;

	Resource deserialize(String content, Class<?> resourceType) throws Exception;

	String getExtension();

	String getContentType();

}
