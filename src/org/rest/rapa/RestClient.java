package org.rest.rapa;

import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;

public class RestClient {

	private final FormatHandler formatHandler;
	private final Url resourceUrl;
	private final HttpMethodExecutor httpMethodExecutor;

		
	public RestClient(Url url, FormatHandler formatHandler,HttpMethodExecutor httpMethodExecutor) {

		this.formatHandler = formatHandler;
		resourceUrl = url;
		this.httpMethodExecutor = httpMethodExecutor;
	}	

	public void save(Resource resource) throws RestClientException {
		try {
			httpMethodExecutor.post(formatHandler.serialize(resource), resourceUrl.getURL(), formatHandler
					.getContentType());
		} catch (Exception e) {
			throw new RestClientException("Error while saving resource", e);
		}
	}

	public void update(Resource resource) throws RestClientException {
		try {
			httpMethodExecutor.put(formatHandler.serialize(resource),
					resourceUrl.getResourceSpecificURL(resource.getId()), formatHandler
							.getContentType());			
		} catch (Exception e) {
			throw new RestClientException("Error while updating resource", e);
		}
	}

	public void delete(Resource resource) throws RestClientException {
		try {
			httpMethodExecutor.delete(resourceUrl.getResourceSpecificURL(resource.getId()));			
		} catch (Exception e) {
			throw new RestClientException("Error while deleting resource", e);
		}
	}

	public Resource getById(int id, Class<?> type) throws RestClientException {
		Resource resource;
		try {
			resource = formatHandler.deserialize(httpMethodExecutor
					.get(resourceUrl.getResourceSpecificURL(id)), type);
		} catch (Exception e) {
			throw new RestClientException("Error while getting resource", e);
		}
		return resource;
	}

}
