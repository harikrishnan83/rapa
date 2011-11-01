package org.rest.rapa;

import org.apache.commons.httpclient.methods.*;

public class HttpMethodProvider {

	public GetMethod getMethod() {
		return new GetMethod();
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
