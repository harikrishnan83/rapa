package org.rest.rapa;

import static org.junit.Assert.*;
import java.net.MalformedURLException;
import org.junit.Test;

public class UrlTest {

	private static final int ID = 1;
	private static final String EXTENSION = "xml";
	private static final String BASEURL = "http://www.test.com";
	private Url url;

	@Test
	public void shouldReturnResourceSpecificUrlWithExtension() {
		url = new Url(BASEURL, EXTENSION, true);
		assertEquals("", url.getResourceSpecificURL(ID));
	}

	@Test
	public void shouldReturnResourceSpecificUrlWithOutExtension() {
		url = new Url(BASEURL, EXTENSION, false);
		assertEquals(BASEURL + "/" + ID, url.getResourceSpecificURL(ID));
	}

	@Test
	public void shouldReturnUrlWithExtension() {
		url = new Url(BASEURL, EXTENSION, true);
		assertEquals(BASEURL + "." + EXTENSION, url.getURL());
	}

	@Test
	public void shouldReturnUrlWithoutExtension() {
		url = new Url(BASEURL, EXTENSION, false);
		assertEquals(BASEURL, url.getURL());
	}

	@Test
	public void ShouldReturnHostNameFromGivenUrl() throws MalformedURLException{
		url = new Url(BASEURL, EXTENSION, false);
		assertEquals("www.test.com", url.getHostName());
	}

	@Test
	public void ShouldReturnPortIfPortIsSpecifiedInUrl() throws MalformedURLException{
		url = new Url("http://test:8080", EXTENSION, false);
		assertEquals(8080, url.getPort());
	}

	@Test
	public void ShouldReturnDefaultPort80IfNoPortIsSpecifiedInUrl() throws MalformedURLException{
		url = new Url("http://test", EXTENSION, false);
		assertEquals(80, url.getPort());
	}

}
