package org.rest.rapa;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.httpclient.HttpException;
import org.rest.rapa.resource.Resource;

public class RestClientWrapper {

	private RestClientCore restClientCore;

	public RestClientWrapper(String url, String username, String password,
			String host, int port) {
		restClientCore = new RestClientCore(url);
		HttpClientAdapter httpClientAdapter = new HttpClientAdapterImpl(
				username, password, host, port);
		restClientCore.setMethodFactory(new MethodFactoryImpl());
		restClientCore.setHttpClientAdapter(httpClientAdapter);
	}

	public RestClientWrapper(String url, String username, String password,
			String host, int port, String scheme) {
		restClientCore = new RestClientCore(url);
		HttpClientAdapter httpClientAdapter = new HttpClientAdapterImpl(
				username, password, host, port, scheme);
		restClientCore.setMethodFactory(new MethodFactoryImpl());
		restClientCore.setHttpClientAdapter(httpClientAdapter);
	}

	public RestClientWrapper(String url, String username, String password,
			String host, int port, String scheme, String realm) {
		restClientCore = new RestClientCore(url);
		HttpClientAdapter httpClientAdapter = new HttpClientAdapterImpl(
				username, password, host, port, scheme, realm);
		restClientCore.setMethodFactory(new MethodFactoryImpl());
		restClientCore.setHttpClientAdapter(httpClientAdapter);
	}

	public void save(Resource resource) throws RestClientException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		try {
			restClientCore.save(resource);
		} catch (HttpException e) {
			throw new RestClientException("Error while saving resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while saving resource", e);
		}
	}

	public void update(Resource resource) throws IllegalAccessException,
			InvocationTargetException, Throwable {
		try {
			restClientCore.update(resource);
		} catch (HttpException e) {
			throw new RestClientException("Error while updating resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while updating resource", e);
		}
	}

	public void delete(Resource resource) throws RestClientException {
		try {
			restClientCore.delete(resource);
		} catch (HttpException e) {
			throw new RestClientException("Error while deleting resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while deleting resource", e);
		}
	}

	public Resource getById(int id, Class clazz) throws RestClientException {
		Resource resource = null;
		try {
			resource = (Resource) restClientCore.getById(id, clazz);
		} catch (HttpException e) {
			throw new RestClientException("Error while getting resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while getting resource", e);
		}
		return resource;
	}

}
