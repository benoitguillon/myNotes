package org.bgi.hibernate.team;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.bgi.hibernate.common.Person;

@Entity
public class BoardMembership {
	
	@Id
	private Long id;
	
	@ManyToOne
	private Person person;
	
	private BoardRole role;

	@ManyToOne
	private Team team;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public BoardRole getRole() {
		return role;
	}

	public void setRole(BoardRole role) {
		this.role = role;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
