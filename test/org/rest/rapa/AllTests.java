package org.rest.rapa;

import org.rest.rapa.resource.RestClientutilTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.rest.rapa");
		//$JUnit-BEGIN$
		suite.addTestSuite(MethodFactoryTest.class);
		suite.addTestSuite(RestClientCoreTest.class);
		suite.addTestSuite(RestClientutilTest.class);
		//$JUnit-END$
		return suite;
	}

}
