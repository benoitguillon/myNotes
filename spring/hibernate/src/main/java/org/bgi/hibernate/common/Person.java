package org.bgi.hibernate.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String nickName;
	
	@NotNull
	private LocalDate birthDate;
	
	@NotNull
	@ManyToOne
	private Country birthCountry;
	
	@OneToMany
	@OrderColumn(name="nat_order")
	private List<Country> nationalities = new ArrayList<Country>();

	public Person(){}
	
	public Person(String firstName, String lastName, String nickName, LocalDate birthDate, Country birthCountry){
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickName = nickName;
		this.birthDate = birthDate;
		this.birthCountry = birthCountry;
	}
	
	public Person addNationality(Country nationality){
		this.nationalities.add(nationality);
		return this;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Country getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(Country birthCountry) {
		this.birthCountry = birthCountry;
	}

	public List<Country> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<Country> nationalities) {
		this.nationalities = nationalities;
	}
	
	
}
