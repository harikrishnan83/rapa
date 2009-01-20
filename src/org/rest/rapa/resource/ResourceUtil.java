package org.rest.rapa.resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.httpclient.NameValuePair;

public class ResourceUtil {

	public static NameValuePair[] getNameValuePairs(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class clazz = o.getClass();
		Method[] methods = clazz.getMethods();
		NameValuePair[] nameValuePairs = new NameValuePair[methods.length];
		int i = 0;
		for (Method method: methods) {
			String methodName = method.getName();
			if (methodName.contains("get") && !methodName.equals("getClass")) {
				Object value = method.invoke(o);
				nameValuePairs[i++] = new NameValuePair(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1).toLowerCase() + "[" + methodName.substring(methodName.indexOf("t") + 1).toLowerCase() + "]", value==null?null:value.toString());
			}
		}
		NameValuePair[] truncatedNameValuePairs = new NameValuePair[i];
		System.arraycopy(nameValuePairs, 0, truncatedNameValuePairs, 0, i);
		System.out.println(truncatedNameValuePairs.length);
		return truncatedNameValuePairs;
	}
	
}
