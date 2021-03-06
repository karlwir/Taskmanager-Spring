package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Issue;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.IssueRepository;

@Component
public final class IssueService extends BaseService<Issue, IssueRepository> {

	@Autowired
	public IssueService(IssueRepository ir, ServiceTransaction st) {
		super(ir, st);
	}
	
	public Issue createIssue(WorkItem workItem, String description) throws ServiceException {
		if (workItem.getStatus() == Status.DONE) {
			return transaction(() -> {
				workItemService.updateStatus(workItem, Status.UNSTARTED);
				return repository.save(new Issue(workItem, description));
			});
		} else {
			throw new ServiceException("Invalid workitem status");
		}
	}
	
	public Collection<Issue> getByWorkItem(WorkItem workItem) throws ServiceException {
		return execute(() -> repository.findByWorkItemId(workItem.getId()));
	}

	public Issue updateDescription(Issue issue, String description) throws ServiceException {
		return execute(() -> {
			issue.setDescription(description);
			return repository.save(issue);
		});
	}

	public Issue updateStatusClosed(Issue issue) throws ServiceException {
		return execute(() -> {
			issue.setOpenIssue(false);
			return repository.save(issue);
		});
	}

	public Issue updateStatusOpen(Issue issue) throws ServiceException {
		return execute(() -> {
			issue.setOpenIssue(true);
			return repository.save(issue);
		});
	}
}
