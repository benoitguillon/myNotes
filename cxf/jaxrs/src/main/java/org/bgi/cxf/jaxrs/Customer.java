package org.bgi.cxf.jaxrs;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement("customer")
public class Customer {
	
	private long id;
	
	private String name;
	
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
