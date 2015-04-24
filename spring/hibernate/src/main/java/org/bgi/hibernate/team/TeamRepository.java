package org.bgi.hibernate.team;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="teams")
public interface TeamRepository extends CrudRepository<Team, Long> {

}
