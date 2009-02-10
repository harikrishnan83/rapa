package org.rest.rapa;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.httpclient.HttpException;
import org.rest.rapa.resource.Resource;



public class RestClientWrapper<ResourceType extends Resource> {

	private RestClientCore<ResourceType> restClientCore;

	public RestClientWrapper(String url, String username, String password,
			String host, int port) {
		restClientCore = new RestClientCore<ResourceType>(url);
		HttpClientAdapter httpClientAdapter = new HttpClientAdapterImpl(
				username, password, host, port);
		restClientCore.setMethodFactory(new MethodFactoryImpl());
		restClientCore.setHttpClientAdapter(httpClientAdapter);
	}

	public RestClientWrapper(String url, String username, String password,
			String host, int port, String scheme) {
		restClientCore = new RestClientCore<ResourceType>(url);
		HttpClientAdapter httpClientAdapter = new HttpClientAdapterImpl(
				username, password, host, port, scheme);
		restClientCore.setMethodFactory(new MethodFactoryImpl());
		restClientCore.setHttpClientAdapter(httpClientAdapter);
	}

	public RestClientWrapper(String url, String username, String password,
			String host, int port, String scheme, String realm) {
		restClientCore = new RestClientCore<ResourceType>(url);
		HttpClientAdapter httpClientAdapter = new HttpClientAdapterImpl(
				username, password, host, port, scheme, realm);
		restClientCore.setMethodFactory(new MethodFactoryImpl());
		restClientCore.setHttpClientAdapter(httpClientAdapter);
	}

	public void save(ResourceType resource) throws RestClientException {
		try {
			restClientCore.save(resource);
		} catch (HttpException e) {
			throw new RestClientException("Error while saving resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while saving resource", e);
		} catch (IllegalArgumentException e) {
			throw new RestClientException("Error while saving resource", e);
		} catch (IllegalAccessException e) {
			throw new RestClientException("Error while saving resource", e);
		} catch (InvocationTargetException e) {
			throw new RestClientException("Error while saving resource", e);
		}
	}

	public void update(ResourceType resource) throws RestClientException {
		try {
			restClientCore.update(resource);
		} catch (HttpException e) {
			throw new RestClientException("Error while updating resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while updating resource", e);
		} catch (IllegalArgumentException e) {
			throw new RestClientException("Error while saving resource", e);
		} catch (IllegalAccessException e) {
			throw new RestClientException("Error while saving resource", e);
		} catch (InvocationTargetException e) {
			throw new RestClientException("Error while saving resource", e);
		}
	}

	public void delete(ResourceType resource) throws RestClientException {
		try {
			restClientCore.delete(resource);
		} catch (HttpException e) {
			throw new RestClientException("Error while deleting resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while deleting resource", e);
		}
	}

	public ResourceType getById(int id, Class<ResourceType> type) throws RestClientException {
		ResourceType resource = null;
		try {
			resource = restClientCore.getById(id, type);
		} catch (HttpException e) {
			throw new RestClientException("Error while getting resource", e);
		} catch (IOException e) {
			throw new RestClientException("Error while getting resource", e);
		}
		return resource;
	}

}
