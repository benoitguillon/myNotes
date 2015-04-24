package org.bgi.hibernate.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="countries")
public interface CountryRepository extends CrudRepository<Country, Long> {
	
	public Country findByCode(String code);

}
