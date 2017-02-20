package se.jdr.repository;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;

import se.jdr.model.Issue;

public interface IssueRepository extends PagingAndSortingRepository<Issue, Long> {

	Collection<Issue> findByWorkItemId(Long id);
}
