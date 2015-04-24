package org.bgi.hibernate.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Country {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(unique=true)
	private String name;
	
	@NotNull
	@Column(unique=true)
	private String code;
	
	public Country(){}
	
	public Country(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
