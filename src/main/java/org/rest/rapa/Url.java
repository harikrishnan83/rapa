package org.rest.rapa;

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

}
