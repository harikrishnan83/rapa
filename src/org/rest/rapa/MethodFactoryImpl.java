package org.rest.rapa;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

public class MethodFactoryImpl implements MethodFactory {

	public GetMethod createGetMethod(String url) {
		return new GetMethod(url);
	}

	public PostMethod createPostMethod(String url) {
		return new PostMethod(url);
	}

	public DeleteMethod createDeleteMethod(String url) {
		return new DeleteMethod(url);
	}

	public PutMethod createPutMethod(String url) {
		return new PutMethod(url);
	}

}
