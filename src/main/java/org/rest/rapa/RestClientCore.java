package org.rest.rapa;

import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;

public class RestClientCore {

	private final Url url;
	private final FormatHandler formatHandler;
	private final HttpMethodExecutor httpMethodExecutor;

	public RestClientCore(Url url, FormatHandler formatHandler,
			HttpMethodExecutor httpMethodExecutor) {
		this.url = url;
		this.formatHandler = formatHandler;
		this.httpMethodExecutor = httpMethodExecutor;
	}

	public Resource getById(int id, Class resource) throws Exception {
		return formatHandler.deserialize(httpMethodExecutor
				.get(url.getResourceSpecificURL(id)), resource);
	}

	public void save(Resource resource) throws Exception {
		httpMethodExecutor.post(formatHandler.serialize(resource), url.getURL(), formatHandler
				.getContentType());
	}

	public void update(Resource resource) throws Exception {
		httpMethodExecutor.put(formatHandler.serialize(resource),
				url.getResourceSpecificURL(resource.getId()), formatHandler
						.getContentType());
	}

	public void delete(Resource resource) throws Exception {
		httpMethodExecutor.delete(url.getResourceSpecificURL(resource.getId()));
	}

}