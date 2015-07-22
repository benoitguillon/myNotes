// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.bgi.hibernate.cache;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;


@Path("/contacts")
public class ContactService {

    private ContactDao contactDao;
    
    private static final Log LOG = LogFactory.getLog(ContactService.class); 
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) throws Exception {
    	LOG.info(String.format("Getting a contact with id %d", id));
        Contact result = contactDao.getById(id);
        if(result == null){
        	return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(result).build();
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("text") String text) throws Exception {
    	LOG.info("");
    	List<Contact> result = new ArrayList<Contact>();
    	if(!StringUtils.isEmpty(text)){
    		result = contactDao.findFullText(text);
    	}
    	else {
    		result = contactDao.findAll();
    	}
    	return Response.ok(new GenericEntity<List<Contact>>(result){}).build();
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Contact c) throws Exception {
    	LOG.info("Saving a new contact");
        long id = contactDao.save(c);
        return Response.ok(id).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long id, Contact c) throws Exception {
    	LOG.info(String.format("Updating contact with id %d", id));
    	Contact current = contactDao.getById(id);
    	if(current == null){
    		return Response.status(Status.NOT_FOUND).build();
    	}
    	if(c.getId() != id){
    		return Response.status(Status.BAD_REQUEST).build();
    	}
    	current.setAddress(c.getAddress());
    	current.setCity(c.getCity());
    	current.setCountry(c.getCountry());
    	current.setFirstName(c.getFirstName());
    	current.setLastName(c.getLastName());
    	current.setZipCode(c.getZipCode());
    	contactDao.update(current);
    	return Response.ok().build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) throws Exception {
    	LOG.info(String.format("Deleting contact with id %d", id));
    	Contact current = contactDao.getById(id);
    	if(current == null){
    		return Response.status(Status.NOT_FOUND).build();
    	}
    	contactDao.delete(current);
    	return Response.ok().build();
    }
    
    @DELETE
    @Path("/")
    public Response deleteAll() throws Exception {
    	LOG.info("Deleting all contacts");
    	contactDao.deleteAll();
    	return Response.ok().build();
    }
    
    public ContactDao getContactDao() {
        return contactDao;
    }

    
    public void setContactDao(ContactDao contactDao) {
        this.contactDao = contactDao;
    }
    
    
}
