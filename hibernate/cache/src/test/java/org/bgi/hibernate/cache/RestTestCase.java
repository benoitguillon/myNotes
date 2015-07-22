package org.bgi.hibernate.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Before;

public abstract class RestTestCase {
	
	//public static final String BASE_URL = "http://localhost:8888/cache";
	
	public static final String BASE_URL = "http://localhost:8080/cache";
	
	private List<Object> providers = new ArrayList<Object>();
	
	public RestTestCase(){
		providers.add(new JacksonJaxbJsonProvider());
	}
	
	@Before
	public void setUp() throws Exception {
		Response response = WebClient.create(BASE_URL + "/contacts").delete();
		Assert.assertEquals(200, response.getStatus());
	}
	
	protected List<Object> getJaxrsProviders(){
		return this.providers;
	}
	
	protected long saveContact(Contact c){
		String responseContent = WebClient.create(BASE_URL + "/contacts", getJaxrsProviders())
		.type(MediaType.APPLICATION_JSON).post(c, String.class);
		return Long.parseLong(responseContent);
	}
	
	protected Contact getContactById(long id){
		Contact c = WebClient.create(BASE_URL + "/contacts/" + id, providers)
				.accept(MediaType.APPLICATION_JSON).get(Contact.class);
		return c;
	}
	
	protected void updateContact(Contact c){
		Response response = WebClient.create(BASE_URL + "/contacts/" + c.getId(), getJaxrsProviders())
				.type(MediaType.APPLICATION_JSON).put(c);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	protected Collection<? extends Contact> findAllContacts(){
		return WebClient.create(RestTestCase.BASE_URL + "/contacts", this.getJaxrsProviders())
				.accept(MediaType.APPLICATION_JSON).getCollection(Contact.class);
	}
	
	protected Collection<? extends Contact> findAllMatchingText(String text){
		return WebClient.create(RestTestCase.BASE_URL + "/contacts", this.getJaxrsProviders())
				.query("text", text)
				.accept(MediaType.APPLICATION_JSON).getCollection(Contact.class);
	}
	
	protected void reindex() {
		Response response = WebClient.create(RestTestCase.BASE_URL + "/admin/index", this.getJaxrsProviders()).post("");
		Assert.assertEquals(200, response.getStatus());
	}
	
	
	
}
