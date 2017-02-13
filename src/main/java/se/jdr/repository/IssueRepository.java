package se.jdr.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import se.jdr.model.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {

	Collection<Issue> findByWorkItemId(Long id);
}
