import java.lang.reflect.InvocationTargetException;

import org.rest.rapa.RestClientException;
import org.rest.rapa.RestClientWrapper;


public class Customers2 {
	
	public static void main(String args[]) {
		Customer customer = new Customer();
		customer.setName("Hari");
		RestClientWrapper restClientWrapper = new RestClientWrapper("http://localhost:3000/customers", "", "", "localhost", 9000);
		try {
			restClientWrapper.save(customer);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			customer = (Customer)restClientWrapper.getById(1, Customer.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Hi");
		System.out.println(customer.getName());
		
		customer = new Customer();
		customer.setName("hi2");
		customer.setId(3);
		try {
			restClientWrapper.update(customer);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		customer = new Customer();
		customer.setId(11);
		try {
			restClientWrapper.delete(customer);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
 