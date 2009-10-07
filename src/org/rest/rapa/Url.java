package org.rest.rapa;

import java.net.MalformedURLException;
import java.net.URL;

public class Url {
	private String baseUrl;
	private String extension;
	private boolean appendExtension;

	public Url(String baseUrl, String extension, boolean appendExtension) {
		super();
		this.appendExtension = appendExtension;
		this.baseUrl = baseUrl;
		this.extension = extension;
	}

	public String getResourceSpecificURL(int id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(baseUrl);
		stringBuilder.append("/");
		stringBuilder.append(id);
		appendExtension(stringBuilder);
		return stringBuilder.toString();
	}

	public String getURL() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(baseUrl);
		appendExtension(stringBuilder);
		return stringBuilder.toString();
	}

	private void appendExtension(StringBuilder stringBuilder) {
		if (appendExtension) {
			stringBuilder.append(".");
			stringBuilder.append(extension);
		}
	}

	public String getHostName() throws MalformedURLException {
		return new URL(baseUrl).getHost();
	}

	public int getPort() throws MalformedURLException {
		int port = new URL(baseUrl).getPort();
		int defaultPort = 80;
		if (isPortSpecifiedInUrl(port))
		{
			return defaultPort;
		}
		return port;
	}

	private boolean isPortSpecifiedInUrl(int port) {
		return port == -1;
	}

}
