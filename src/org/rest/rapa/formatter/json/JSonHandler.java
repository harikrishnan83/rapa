package org.rest.rapa.formatter.json;

import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;

import com.google.gson.Gson;

public class JSonHandler implements FormatHandler {

	public Resource deserialize(String content,
			Class<? extends Resource> resourceType) {
		if (content == null || content.trim().length() == 0) {
			return null;
		}
		return new Gson().fromJson(content, resourceType);
	}

	public String serialize(Resource resource) {
		if (resource == null) {
			return null;
		}
		return new Gson().toJson(resource);
	}

	public String getExtension() {
		return "json";
	}

	public String getContentType() {
		return "text/json";
	}

}
