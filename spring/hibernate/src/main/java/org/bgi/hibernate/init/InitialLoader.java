package org.bgi.hibernate.init;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.bgi.hibernate.common.Country;
import org.bgi.hibernate.common.CountryRepository;
import org.bgi.hibernate.common.Person;
import org.bgi.hibernate.common.PersonRepository;
import org.bgi.hibernate.team.BoardRole;
import org.bgi.hibernate.team.Team;
import org.bgi.hibernate.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class InitialLoader {

	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	private Random random = new Random(System.currentTimeMillis());
	
	private List<Country> countries = new ArrayList<Country>();
	
	@Transactional
	@PostConstruct
	public void initializeCommonData(){
		initCountries();
		initTeams();
	}
	
	private void initCountries() {
		saveCountry(new Country("France", "FR"));
		saveCountry(new Country("Italy", "IT"));
		saveCountry(new Country("Brasil", "BR"));
		saveCountry(new Country("Portugal", "PT"));
		saveCountry(new Country("Spain", "ES"));
		saveCountry(new Country("Qatar", "QT"));
	}
	
	private void initTeams() {
		for(Country country : countries){
			for(int i=0; i<20; i++){
				Team t = createTeam(country, i);
			}
		}
	}
	
	private void saveCountry(Country c){
		c = countryRepo.save(c);
		countries.add(c);
	}
	
	private Team createTeam(Country c, int iteration) {
		Team t = new Team();
		t.setCountry(c);
		t.setLongName(c.getName() + " team " + (iteration+1));
		t.setShortName(c.getCode() + " " + (iteration+1));
		Person president = createPerson(iteration + 1000);
		t.addBoardMember(president, BoardRole.PRESIDENT);
		return this.teamRepo.save(t);
	}
	
	private Person createPerson(int iteration) {
		Person p = new Person();
		p.setFirstName("firstName" + iteration);
		p.setLastName("lastName" + iteration);
		p.setNickName(null);
		p.setBirthDate(generateRandomBirthDate());
		p.setBirthCountry(pickupRandomCountry());
		return this.personRepo.save(p);
	}
	
	private Country pickupRandomCountry(){
		int index = random.nextInt(this.countries.size() - 1);
		return this.countries.get(index);
	}
	
	private LocalDate generateRandomBirthDate(){
		int ageInDays = random.nextInt((MAX_AGE - MIN_AGE) * DAYS_PER_YEAR) + (MIN_AGE * DAYS_PER_YEAR);
		return LocalDate.now().minusDays(ageInDays);
	}
	
	
	private static final int DAYS_PER_YEAR = 365; 
	private static final int MIN_AGE = 20;
	private static final int MAX_AGE = 70;
}
