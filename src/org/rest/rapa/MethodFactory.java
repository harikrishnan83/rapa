package org.rest.rapa;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

public interface MethodFactory {

	public abstract GetMethod createGetMethod(String url);

	public abstract PostMethod createPostMethod(String url);

	public abstract DeleteMethod createDeleteMethod(String url);

	public abstract PutMethod createPutMethod(String url);

}