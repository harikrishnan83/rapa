package org.rest.rapa;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.rest.rapa.resource.Resource;
import org.rest.rapa.formatter.FormatHandlerFactory;

import java.io.IOException;

public class RestClientWrapper {

	private final RestClientCore restClientCore;
	private final FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();

	public RestClientWrapper(String url, String username, String password,
			String host, int port, String format) {
		restClientCore = new RestClientCore(url, formatHandlerFactory
				.create(format), new HttpMethodExecutor(
				new HttpClientAdapterImpl(username, password, host, port),
				new GetMethod(), new PostMethod(), new DeleteMethod(),
				new PutMethod()));
	}

	public RestClientWrapper(String url, String username, String password,
			String host, int port, String scheme, String format) {
		restClientCore = new RestClientCore(url, formatHandlerFactory
				.create(format), new HttpMethodExecutor(
				new HttpClientAdapterImpl(username, password, host, port,
						scheme), new GetMethod(), new PostMethod(),
				new DeleteMethod(), new PutMethod()));
	}

	public RestClientWrapper(String url, String username, String password,
			String host, int port, String scheme, String realm, String format) {
		restClientCore = new RestClientCore(url, formatHandlerFactory
				.create(format), new HttpMethodExecutor(
				new HttpClientAdapterImpl(username, password, host, port,
						realm, scheme), new GetMethod(), new PostMethod(),
				new DeleteMethod(), new PutMethod()));
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
		} catch (IOException e) {
			throw new RestClientException("Error while updating resource", e);
		} catch (Exception e) {
			throw new RestClientException("Error while saving resource", e);
		}
	}

	public void delete(Resource resource) throws RestClientException {
		try {
			restClientCore.delete(resource);
		} catch (IOException e) {
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
