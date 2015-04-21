package org.bgi.hibernate.init;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.bgi.hibernate.common.Country;
import org.bgi.hibernate.common.CountryRepository;
import org.bgi.hibernate.common.Person;
import org.bgi.hibernate.common.PersonRepository;
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
	
	@Transactional
	@PostConstruct
	public void initializeCommonData(){
		initCountries();
		initPersons();
	}
	
	private void initCountries() {
		countryRepo.save(new Country("France", "FR"));
		countryRepo.save(new Country("Italy", "IT"));
		countryRepo.save(new Country("Brasil", "BR"));
		countryRepo.save(new Country("Portugal", "PT"));
		countryRepo.save(new Country("Spain", "ES"));
		countryRepo.save(new Country("Qatar", "QT"));
	}
	
	private void initPersons() {
		personRepo.save(new Person("Nasser", "Al-Khelaifi", null, LocalDate.of(1973, 11, 12), countryRepo.findByCode("QT")));
	}
}
