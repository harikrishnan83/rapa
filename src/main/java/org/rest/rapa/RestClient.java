package org.rest.rapa;

import org.rest.rapa.formatter.FormatHandler;
import org.rest.rapa.formatter.FormatHandlerFactory;
import org.rest.rapa.formatter.Formats;
import org.rest.rapa.resource.Resource;

public class RestClient {

	private final RestClientCore restClientCore;
	private final FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();

	public RestClient(String url, String username, String password,
			String host, int port, String scheme, String realm, Formats format, boolean useFormatAsExtension) {

		FormatHandler formatHandler = formatHandlerFactory.create(format);
		Url resourceUri = new Url(url, format.toString(), useFormatAsExtension);
		HttpClientAdapterImpl httpClientAdapter = new HttpClientAdapterImpl(
				username, password, host, port, realm, scheme);
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(
				httpClientAdapter);

		restClientCore = new RestClientCore(resourceUri, formatHandler,
				httpMethodExecutor);
	}

	public void save(Resource resource) throws RestClientException {
		try {
			restClientCore.save(resource);
		} catch (Exception e) {
			throw new RestClientException("Error while saving resource", e);
		}
	}

	public void update(Resource resource) throws RestClientException {
		try {
			restClientCore.update(resource);
		} catch (Exception e) {
			throw new RestClientException("Error while updating resource", e);
		}
	}

	public void delete(Resource resource) throws RestClientException {
		try {
			restClientCore.delete(resource);
		} catch (Exception e) {
			throw new RestClientException("Error while deleting resource", e);
		}
	}

	public Resource getById(int id, Class type) throws RestClientException {
		Resource resource;
		try {
			resource = restClientCore.getById(id, type);
		} catch (Exception e) {
			throw new RestClientException("Error while getting resource", e);
		}
		return resource;
	}

}
