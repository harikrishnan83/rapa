package org.rest.rapa;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.JAXB;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rest.rapa.resource.Resource;

import static org.rest.rapa.resource.ResourceUtil.*;

public class RestClientCore {

	private String url = "";
	private HttpClientAdapter httpClientAdapter = null;
	private MethodFactory methodFactory = null;
	private Log log = LogFactory.getLog(RestClientCore.class);

	private String getResourceSpecificURL(int id) {
		return url + "/" + id + ".xml";
	}

	private String getXML(String resourceSpecificURL) throws HttpException,
			IOException {
		String ret = "";
		log.debug("Creating GET method for the URL " + resourceSpecificURL);
		GetMethod method = methodFactory.createGetMethod(resourceSpecificURL);
		try {
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

			ret = new String(method.getResponseBody());

		} finally {
			method.releaseConnection();
		}
		return ret;
	}

	private String postXML(NameValuePair[] nameValuePairs) throws HttpException,
			IOException {
		String ret = "";
		log.debug("Creating POST method for the URL " + getURL());
		PostMethod method = methodFactory.createPostMethod(getURL());
		method.addParameters(nameValuePairs);

		try {
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_CREATED) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

		} finally {
			method.releaseConnection();
		}

		return ret;
	}

	private String updateXML(NameValuePair[] nameValuePairs, int id)
			throws HttpException, IOException {
		String ret = "";
		log.debug("Creating PUT method for the URL " + getURL());
		PutMethod method = methodFactory
				.createPutMethod(getResourceSpecificURL(id));
		method.setQueryString(nameValuePairs);

		try {
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_ACCEPTED) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

		} finally {
			method.releaseConnection();
		}

		return ret;
	}

	private void deleteXML(int id) throws HttpException, IOException {
		log.debug("Creating DELETE method for the URL " + getURL());
		DeleteMethod method = methodFactory
				.createDeleteMethod(getResourceSpecificURL(id));

		try {
			// Execute the method.
			int statusCode = getHttpClient().executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Method failed: "
						+ method.getStatusLine());
			}

		} finally {
			method.releaseConnection();
		}
	}

	private String getURL() {
		return url + ".xml";
	}

	private HttpClientAdapter getHttpClient() {
			return httpClientAdapter;
	}

	public RestClientCore(String url) {
		if (isNullOrEmptyString(url)) {
			throw new IllegalArgumentException(
					"URL cannot be null or empty String");
		}
		this.url = url;
	}

	private boolean isNullOrEmptyString(String string) {
		return string == null || string.equals("");
	}

	public Object getById(int id, Class clazz) throws HttpException, IOException {
		return JAXB.unmarshal(new StringReader(getXML(getResourceSpecificURL(id))), clazz);
	}

	public void setMethodFactory(MethodFactory methodFactory) {
		this.methodFactory = methodFactory;
	}

	public void save(Resource resource) throws HttpException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		this.postXML(getNameValuePairs(resource));
	}

	public void update(Resource resource) throws HttpException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		updateXML(getNameValuePairs(resource), resource.getId());
	}

	public void delete(Resource customer) throws HttpException, IOException {
		this.deleteXML(customer.getId());
	}

	public void setHttpClientAdapter(HttpClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}

}