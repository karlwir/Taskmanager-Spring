package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Issue;
import se.jdr.repository.IssueRepository;

@Component
public final class IssueService {
	
	private final IssueRepository issuerepository;
	
	@Autowired
	public IssueService(IssueRepository issueRepository) {
		this.issuerepository = issueRepository;
	}
	
	public Issue addOrUpdate(Issue issue) {
		return issuerepository.save(issue);
	}
	

}
