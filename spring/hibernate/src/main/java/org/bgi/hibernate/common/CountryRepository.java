package org.bgi.hibernate.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
	
	public Country findByCode(String code);

}
