package se.jdr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import se.jdr.model.Team;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {

}
