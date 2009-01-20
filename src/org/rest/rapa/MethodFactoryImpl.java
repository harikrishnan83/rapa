package org.rest.rapa;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

public class MethodFactoryImpl implements MethodFactory {

	/* (non-Javadoc)
	 * @see org.rest.rapa.MethodFactory#createGetMethod(java.lang.String)
	 */
	public GetMethod createGetMethod(String url) {
		return new GetMethod(url);
	}
	
	/* (non-Javadoc)
	 * @see org.rest.rapa.MethodFactory#createPostMethod(java.lang.String)
	 */
	public PostMethod createPostMethod(String url) {
		return new PostMethod(url);
	}
	
	/* (non-Javadoc)
	 * @see org.rest.rapa.MethodFactory#createDeleteMethod(java.lang.String)
	 */
	public DeleteMethod createDeleteMethod(String url) {
		return new DeleteMethod(url);
	}

	/* (non-Javadoc)
	 * @see org.rest.rapa.MethodFactory#createPutMethod(java.lang.String)
	 */
	public PutMethod createPutMethod(String url) {
		return new PutMethod(url);
	}
	
}
