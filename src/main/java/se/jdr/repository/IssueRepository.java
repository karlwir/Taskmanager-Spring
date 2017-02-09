package se.jdr.repository;

import org.springframework.data.repository.CrudRepository;

import se.jdr.model.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {

}
