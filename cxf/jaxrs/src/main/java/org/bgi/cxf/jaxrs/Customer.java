package org.bgi.cxf.jaxrs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="customer")
public class Customer {
	
    @XmlElement(name="internal_id")
	private long id;
	
    @XmlElement(name="real_name")
	private String name;
	
    @XmlElement(name="mail", required=true)
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
