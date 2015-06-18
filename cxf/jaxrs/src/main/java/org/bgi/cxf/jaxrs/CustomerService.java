package org.bgi.cxf.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

@Path("/customers")
public class CustomerService {

	@Path("/nextOne")
	@GET
	public String nextOne() {
		return "nextOne";
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer find(@PathParam("id") long id) throws Exception {
	    if(id == 11){
            throw new RuntimeException("Internal error");
        }
	    else if(id > 20){
	        throw new Exception("Value is too high");
	    }
	    else if(id > 11){
	        throw new WebApplicationException(Status.NOT_FOUND);
	    }
	    else {
	        Customer result = new Customer();
	        result.setId(id);
	        result.setName("Customer " + id);
	        return result;
	    }
		
	}
	
	public class NotFoundException extends RuntimeException {
	    public NotFoundException(String msg){
	        super(msg);
	    }
	}
	
}
