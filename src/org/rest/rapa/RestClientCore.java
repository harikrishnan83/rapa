package org.rest.rapa;

import java.io.IOException;
import org.apache.commons.httpclient.HttpException;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;

public class RestClientCore {

	private String url;
	private FormatHandler formatHandler;
	private HttpMethodExecutor httpMethodExecutor;

	public RestClientCore(String url, HttpClientAdapter httpClientAdapter,
			MethodFactory methodFactory, FormatHandler formatHandler) {
		this.url = url;
		this.formatHandler = formatHandler;
		httpMethodExecutor = new HttpMethodExecutor(methodFactory,
				httpClientAdapter);
	}

	private String getResourceSpecificURL(int id) {
		return url + "/" + id + "." + this.formatHandler.getExtension();
	}

	private String getURL() {
		return url + "." + this.formatHandler.getExtension();
	}

	public Resource getById(int id, Class resource) throws Exception {
		return this.formatHandler.decode(httpMethodExecutor
				.getXML(getResourceSpecificURL(id)), resource);
	}

	public void save(Resource resource) throws Exception {
		String xml = encode(resource);
		httpMethodExecutor.postXML(xml, getURL());
	}

	public void update(Resource resource) throws Exception {
		String xml = encode(resource);
		httpMethodExecutor.updateXML(xml, getResourceSpecificURL(resource
				.getId()));
	}

	private String encode(Resource resource) throws Exception {
		return this.formatHandler.encode(resource);
	}

	public void delete(Resource resource) throws HttpException, IOException {
		httpMethodExecutor.deleteXML(getResourceSpecificURL(resource.getId()));
	}

}