package org.rest.rapa;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.*;
import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.resource.Resource;

public class RestClient {

	private final FormatHandler formatHandler;
	private final Url resourceUrl;
	private final HttpMethodExecutor httpMethodExecutor;
	private Log log = LogFactory.getLog(RestClient.class);

	public RestClient(Url url, FormatHandler formatHandler,
			HttpMethodExecutor httpMethodExecutor) {

		this.formatHandler = formatHandler;
		resourceUrl = url;
		this.httpMethodExecutor = httpMethodExecutor;
	}

	public void save(Resource resource) throws RestClientException {
		try {
			String serializedResource = formatHandler.serialize(resource);
			String url = resourceUrl.getURL();
			String contentType = formatHandler.getContentType();

			String response = httpMethodExecutor.post(serializedResource, url,
					contentType);

			tryToSetIdOnResource(resource, response);
		} catch (Exception e) {
			throw new RestClientException("Error while saving resource", e);
		}

	}

	private void tryToSetIdOnResource(Resource resource, String response) {
		try {
			resource.setId(formatHandler.deserialize(response,
					resource.getClass()).getId());
		} catch (Exception e) {
			log.info(new StringBuilder(
					"Resource id could not be set using response ")
					.append(response), e);
		}
	}

	public void update(Resource resource) throws RestClientException {
		try {
			httpMethodExecutor.put(formatHandler.serialize(resource),
					resourceUrl.getResourceSpecificURL(resource.getId()),
					formatHandler.getContentType());
		} catch (Exception e) {
			throw new RestClientException("Error while updating resource", e);
		}
	}

	public void delete(Resource resource) throws RestClientException {
		try {
			httpMethodExecutor.delete(resourceUrl
					.getResourceSpecificURL(resource.getId()));
		} catch (Exception e) {
			throw new RestClientException("Error while deleting resource", e);
		}
	}

	public Resource getById(int id, Class<? extends Resource> type)
			throws RestClientException {
		return getById(id, type, new HashMap<String, String>());
	}

	public Resource getById(int id, Class<? extends Resource> type,
			Map<String, String> requestHeaders) throws RestClientException {
		try {
			return formatHandler.deserialize(httpMethodExecutor.get(
					resourceUrl.getResourceSpecificURL(id), requestHeaders),
					type);
		} catch (Exception e) {
			throw new RestClientException("Error while getting resource", e);
		}
	}

}
