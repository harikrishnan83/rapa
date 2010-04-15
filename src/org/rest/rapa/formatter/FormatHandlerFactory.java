package org.rest.rapa.formatter;

import org.rest.rapa.formatter.JAXB.XMLHandler;
import org.rest.rapa.formatter.json.JSonHandler;

public class FormatHandlerFactory {

	public FormatHandler create(Formats format) {
		if (Formats.XML == format) {
			return new XMLHandler();
		} else if (Formats.JSON == format) {
			return new JSonHandler();
		}
		return new NullFormatHandler();
	}


}
