package org.rest.rapa;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class UrlTest {
	
	private static final int ID = 1;
	private static final String EXTENSION = "extension";
	private static final String BASEURL = "baseurl";
	private Url url;

	@Test
	public void shouldReturnResourceSpecificUrlWithExtension() {
		url = new Url(BASEURL, EXTENSION, true);
		assertEquals(BASEURL + "/" + ID + "." + EXTENSION, url.getResourceSpecificURL(ID));
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
	
}
