package org.bgi.cxf.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerService {

	@Path("/nextOne")
	@GET
	public String nextOne() {
		return "nextOne";
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Customer find(@PathParam("id") long id){
		Customer result = new Customer();
		result.setId(id);
		result.setName("Customer " + id);
		return result;
	}
	
}
