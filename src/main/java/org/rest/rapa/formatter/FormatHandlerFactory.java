package org.rest.rapa.formatter;

public class FormatHandlerFactory {

	public FormatHandler create(Formats format) {
		FormatHandler handler;
        if (Formats.XML == format) {
			handler = new XMLHandler();
		} else if (Formats.JSON == format) {
			handler = new JSonHandler();
		} else {
            throw new RuntimeException("Unsupported Format " + format + ". Supported formats are : " + Formats.getSupportedFormats());
		}
		return handler;
	}


}
