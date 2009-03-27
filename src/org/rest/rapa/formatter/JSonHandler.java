package org.rest.rapa.formatter;

import org.json.me.JSONException;
import org.json.me.JSONObject;
import org.json.me.util.XML;
import org.rest.rapa.resource.Resource;

public class JSonHandler implements FormatHandler {

	public Resource decode(String content, Class resourceType) throws JSONException {
		if(content == null || content.trim().length() == 0) {
			return null;
		}
		JSONObject jsonContent = new JSONObject(content);
		String xmlContent = XML.toString(jsonContent);
		return new XMLHandler().decode(xmlContent, resourceType);
	}

	public String encode(Resource resource) throws JSONException {
		if(resource == null) {
			return null;
		}
		String encodedXml = new XMLHandler().encode(resource);
		JSONObject json = XML.toJSONObject(encodedXml.trim());
		return json.toString();
	}

	public String getExtension() {
		return "json";
	}
}
