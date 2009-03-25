package org.rest.rapa;

import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.formatter.JSonHandler;
import org.rest.rapa.formatter.XMLHandler;

public class FormatHandlerFactory {

	public FormatHandler create(String format) {
		FormatHandler handler = null;
		if (format.equals("xml")) {
			handler = new XMLHandler();
		}else if (format.equalsIgnoreCase("json")) {
			handler = new JSonHandler();
		}else {
			throw new RuntimeException("Unsupported Format " + format);
		}
		return handler;
	}

}
