package se.jdr.repository;

import org.springframework.data.repository.CrudRepository;

import se.jdr.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

}
