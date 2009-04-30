package org.rest.rapa;

import java.io.IOException;

import javax.sound.sampled.spi.FormatConversionProvider;

import org.apache.commons.httpclient.HttpException;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;

public class RestClientCore {

	private String url;
	private FormatHandler formatHandler;
	private HttpMethodExecutor httpMethodExecutor;

	public RestClientCore(String url, FormatHandler formatHandler,
			HttpMethodExecutor httpMethodExecutor) {
		this.url = url;
		this.formatHandler = formatHandler;
		this.httpMethodExecutor = httpMethodExecutor;
	}

	private String getResourceSpecificURL(int id) {
		return url + "/" + id + "." + this.formatHandler.getExtension();
	}

	private String getURL() {
		return url + "." + this.formatHandler.getExtension();
	}

	public Resource getById(int id, Class resource) throws Exception {
		return this.formatHandler.deserialize(httpMethodExecutor
				.get(getResourceSpecificURL(id)), resource);
	}

	public void save(Resource resource) throws Exception {
		String encodedResource = encode(resource);
		httpMethodExecutor.post(encodedResource, getURL(), formatHandler
				.getContentType());
	}

	public void update(Resource resource) throws Exception {
		String xml = encode(resource);
		httpMethodExecutor.update(xml,
				getResourceSpecificURL(resource.getId()), formatHandler
						.getContentType());
	}

	private String encode(Resource resource) throws Exception {
		return this.formatHandler.serialize(resource);
	}

	public void delete(Resource resource) throws HttpException, IOException {
		httpMethodExecutor.delete(getResourceSpecificURL(resource.getId()));
	}

}