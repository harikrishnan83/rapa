/**
 * Purpose: HttpClient does not expose and interface.
 * Hence we are creating an adapter which exposes the interface.
 * 
 * This is used to mock the HttpClient while testing.
 */

package org.rest.rapa;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

public interface HttpClientAdapter {

	public abstract int executeMethod(HttpMethod method) throws HttpException,
			IOException;

}