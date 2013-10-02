package org.rest.rapa;

import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.*;

public class HttpMethodProvider {

	public GetMethod getMethod(Map<String, String> requestHeaders) {
		GetMethod getMethod = new GetMethod();
		if (!requestHeaders.isEmpty()) {
			for (String headerName : requestHeaders.keySet()) {
				Header header = new Header(headerName,
						requestHeaders.get(headerName));
				getMethod.addRequestHeader(header);
			}
		}
		return getMethod;
	}

	public PostMethod postMethod() {
		return new PostMethod();
	}

	public PutMethod putMethod() {
		return new PutMethod();
	}

	public DeleteMethod deleteMethod() {
		return new DeleteMethod();
	}

}
