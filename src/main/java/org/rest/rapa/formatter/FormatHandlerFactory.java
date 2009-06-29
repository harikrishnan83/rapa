package org.rest.rapa.formatter;

public class FormatHandlerFactory {

	public FormatHandler create(String format) {
		FormatHandler handler;
        if (Formats.XML.name().equalsIgnoreCase(format)) {
			handler = new XMLHandler();
		} else if (Formats.JSON.name().equalsIgnoreCase(format)) {
			handler = new JSonHandler();
		} else {
            throw new RuntimeException("Unsupported Format " + format + ". Supported formats are : " + Formats.getSupportedFormats());
		}
		return handler;
	}


}
