package org.rest.rapa;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.rest.rapa.formatter.JSonHandlerTest;
import org.rest.rapa.formatter.XMLHandlerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { RestClientCoreTest.class, HttpMethodExcecutorTest.class,
		FormatHandlerFactoryTest.class, JSonHandlerTest.class, XMLHandlerTest.class })
public class AllTests {
	public static Test suite() {
		return new JUnit4TestAdapter(AllTests.class);

	}
}
