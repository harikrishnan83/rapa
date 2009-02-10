package org.rest.rapa;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

public interface MethodFactory {

	public GetMethod createGetMethod(String url);

	public PostMethod createPostMethod(String url);

	public DeleteMethod createDeleteMethod(String url);

	public PutMethod createPutMethod(String url);

}