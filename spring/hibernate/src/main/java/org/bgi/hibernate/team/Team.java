package org.bgi.hibernate.team;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.bgi.hibernate.common.Country;

@Entity
public class Team {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	private String longName;
	
	private String shortName;
	
	@NotNull
	@ManyToOne
	private Country country;
	
	@OneToMany(mappedBy="team")
	private Set<BoardMembership> boardMembers;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<BoardMembership> getBoardMembers() {
		return boardMembers;
	}

	public void setBoardMembers(Set<BoardMembership> boardMembers) {
		this.boardMembers = boardMembers;
	}
}
