package org.rest.rapa;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.*;

import java.io.IOException;
import java.util.Map;

public class HttpMethodExecutor {

	private static final String METHOD_FAILED = "Method failed: ";
	private static final String UTF_8 = "UTF-8";
	private static final String CONTENT_TYPE = "Content-type";
	private final HttpClientAdapter httpClientAdapter;
	private HttpMethodProvider httpMethodProvider;
	CacheManager cacheManager;
	Ehcache cache;

	public HttpMethodExecutor(HttpClientAdapter httpClientAdapter,
			HttpMethodProvider httpMethodProvider, Ehcache cache,
			CacheManager cacheManager) {
		this.httpClientAdapter = httpClientAdapter;
		this.httpMethodProvider = httpMethodProvider;
		this.cache = cache;
		this.cacheManager = cacheManager;
	}

	public String get(String url, Map<String, String> requestHeaders) throws IOException {
		return executeGet(url, httpMethodProvider.getMethod(requestHeaders), HttpStatus.SC_OK);
	}

	String post(String content, String url, String contentType)
			throws IOException {
		PostMethod postMethod = httpMethodProvider.postMethod();
		postMethod.setRequestHeader(CONTENT_TYPE, contentType);
		postMethod.setRequestEntity(new StringRequestEntity(content,
				contentType, UTF_8));
		return execute(url, postMethod, HttpStatus.SC_CREATED);
	}

	void put(String xml, String url, String contentType) throws IOException {
		PutMethod putMethod = httpMethodProvider.putMethod();
		putMethod.setRequestHeader(CONTENT_TYPE, contentType);
		putMethod.setRequestEntity(new StringRequestEntity(xml, contentType,
				UTF_8));
		execute(url, putMethod, HttpStatus.SC_OK);
	}

	void delete(String url) throws IOException {
		execute(url, httpMethodProvider.deleteMethod(), HttpStatus.SC_OK);
	}

	private String execute(String url, HttpMethodBase method, int statusToCheck)
			throws IOException {
		method.setURI(new URI(url, false));
		try {
			int statusCode = httpClientAdapter.executeMethod(method);
			if (statusCode != statusToCheck) {
				throw new RuntimeException(METHOD_FAILED
						+ method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();
			return responseBody != null ? new String(responseBody) : "";
		} finally {
			method.releaseConnection();
		}
	}

	private String executeGet(String url, HttpMethodBase method,
			int statusToCheck) throws IOException {

		Element cachedElement = cache.get(url);
		if (cachedElement != null) {
			return (String) cachedElement.getObjectValue();
		}

		method.setURI(new URI(url, false));
		try {
			int statusCode = httpClientAdapter.executeMethod(method);
			if (statusCode != statusToCheck) {
				throw new RuntimeException(METHOD_FAILED
						+ method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();

			String response = responseBody != null ? new String(responseBody)
					: "";

			insertToCache(method, url, response);

			return response;
		} finally {
			method.releaseConnection();
		}

	}

	private void insertToCache(HttpMethodBase method, String key, String value) {
		HeaderElement[] cacheControlHeaderElements = method.getResponseHeader(
				"Cache-Control").getElements();

		int maxAge = getMaxAge(cacheControlHeaderElements);

		if (maxAge > 0) {
			Element element = new Element(key, value);
			element.setTimeToLive(maxAge);
			cache.put(element);
		}
	}

	private int getMaxAge(HeaderElement[] cacheControlHeaderElements) {
		for (int i = 0; i < cacheControlHeaderElements.length; i++) {
			HeaderElement headerElement = cacheControlHeaderElements[i];
			if (headerElement.getName().equals("max-age")) {
				String maxAge = headerElement.getValue().toString();
				return Integer.parseInt(maxAge);
			}
		}
		return 0;
	}

	@Override
	protected void finalize() throws Throwable {
		if (cacheManager != null) {
			cacheManager.shutdown();
		}
		super.finalize();
	}

}
