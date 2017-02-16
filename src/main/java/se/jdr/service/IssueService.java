package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Issue;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.IssueRepository;

@Component
public final class IssueService {

	private final IssueRepository issueRepository;
	private final  ServiceManager serviceManager;
	private final ServiceTransaction transaction;

	@Autowired
	public IssueService(IssueRepository issueRepository, ServiceManager serviceManager, ServiceTransaction transaction) {
		this.issueRepository = issueRepository;
		this.serviceManager = serviceManager;
		this.transaction = transaction;
	}
	
	public Issue addOrUpdate(Issue issue) {
		return issueRepository.save(issue);
	}

	public Issue addIssue(WorkItem workItem, String description) throws ServiceException {
		if (workItem.getStatus() == Status.DONE) {
			return transaction.execute(() -> {
				workItem.getUpdater(serviceManager.getWorkItemService()).setStatus(Status.UNSTARTED);
				return addOrUpdate(new Issue(workItem, description));
			});
		} else {
			throw new ServiceException("Invalid work item status");
		}
	}
	
	public Collection<Issue> getByWorkItem(WorkItem workItem) {
		return issueRepository.findByWorkItemId(workItem.getId());
	}

	public Issue updateDescription(Issue issue, String description) {
		return issue.getUpdater(this).setDescription(description);
	}

	public Issue updateStatus(Issue issue, boolean status) {
		return issue.getUpdater(this).setOpenIssue(status);
	}
}
