package org.rest.rapa;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.rest.rapa.resource.RestClientutilTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	MethodFactoryTest.class,
	RestClientCoreTest.class,
	RestClientutilTest.class
})
public class AllTests {
	public static Test suite() {
		return new JUnit4TestAdapter(AllTests.class);

	}
}
