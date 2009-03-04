package org.rest.rapa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.JAXB;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rest.rapa.resource.Resource;

public class RestClientCore {

	private String url = "";
	private HttpClientAdapter httpClientAdapter = null;
	private MethodFactory methodFactory = null;
	private Log log = LogFactory.getLog(RestClientCore.class);
	
	public RestClientCore(String url) {
		if (isNullOrEmptyString(url)) {
			throw new IllegalArgumentException(
					"URL cannot be null or empty String");
		}
		this.url = url;
	}

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

	private String postXML(String xml) throws HttpException,
			IOException {
		String ret = "";
		log.debug("Creating POST method for the URL " + getURL());
		PostMethod method = methodFactory.createPostMethod(getURL());
		method.setRequestHeader("Content-type" , "text/xml");
		method.setRequestBody(xml);

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

	private String updateXML(String xml, int id)
			throws HttpException, IOException {
		String ret = "";
		log.debug("Creating PUT method for the URL " + getURL());
		PutMethod method = methodFactory
				.createPutMethod(getResourceSpecificURL(id));
		method.setRequestHeader("Content-type" , "text/xml");
		method.setRequestBody(xml);

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

	private boolean isNullOrEmptyString(String string) {
		return string == null || string.equals("");
	}

	public Resource getById(int id,Class resource) throws HttpException, IOException {
		return  (Resource)JAXB.unmarshal(new StringReader(getXML(getResourceSpecificURL(id))),resource);
	}
	
	public void save(Resource resource) throws HttpException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		OutputStream outputStream = new ByteArrayOutputStream();
		JAXB.marshal(resource, outputStream);
		String xml = outputStream.toString();
		this.postXML(xml);
	}

	public void update(Resource resource) throws HttpException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		OutputStream outputStream = new ByteArrayOutputStream();
		JAXB.marshal(resource, outputStream);
		String xml = outputStream.toString();
		updateXML(xml, resource.getId());
	}

	public void delete(Resource resource) throws HttpException, IOException {
		this.deleteXML(resource.getId());
	}

	public void setHttpClientAdapter(HttpClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}
	
	public void setMethodFactory(MethodFactory methodFactory) {
		this.methodFactory = methodFactory;
	}


}