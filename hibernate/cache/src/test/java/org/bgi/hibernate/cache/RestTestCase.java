package org.bgi.hibernate.cache;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Assert;
import org.junit.Test;

public class RestTestCase {

	@Test
	public void test1() throws Exception {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		long id = 5;
		Assert.assertTrue(id > 0);
		
		for(int i=0; i<300; i++){
			String newAddress = "NEW_ADRESS_" + i;
			
			Contact getResponse = WebClient.create("http://localhost:8888/cache/contacts/" + id, providers)
			.accept(MediaType.APPLICATION_JSON).get(Contact.class);
			
			Assert.assertNotNull(getResponse);
			Assert.assertEquals(id, getResponse.getId());
			
			getResponse.setAddress(newAddress);
			
			Response updateResponse = WebClient.create("http://localhost:8888/cache/contacts/" + id, providers)
			.type(MediaType.APPLICATION_JSON).put(getResponse);
			
			Assert.assertEquals(Status.OK.getStatusCode(), updateResponse.getStatus());
			
			Thread.sleep(100);
			
			getResponse = WebClient.create("http://localhost:8888/cache/contacts/" + id, providers)
					.accept(MediaType.APPLICATION_JSON).get(Contact.class);
			
			Assert.assertEquals(newAddress, getResponse.getAddress());
		}
		
		
		
	}
	
}
