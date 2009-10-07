package org.rest.rapa.formatter;

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
