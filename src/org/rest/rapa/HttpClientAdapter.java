/**
 * Purpose: HttpClient does not expose and interface.
 * Hence we are creating an adapter which exposes the interface.
 * 
 * This is used to mock the HttpClient while testing.
 */

package org.rest.rapa;

import org.apache.commons.httpclient.HttpMethod;

import java.io.IOException;

public interface HttpClientAdapter {

	public int executeMethod(HttpMethod method) throws IOException;

}