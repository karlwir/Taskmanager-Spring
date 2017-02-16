package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Issue;
import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.WorkItemRepository;

@Component
public final class WorkItemService {

	private final WorkItemRepository workItemRepository;
	private final ServiceTransaction transaction;
	private final ServiceManager serviceManager;

	@Autowired
	public WorkItemService(WorkItemRepository workItemRepository, ServiceManager serviceManager, ServiceTransaction transaction) {
		this.workItemRepository = workItemRepository;
		this.serviceManager = serviceManager;
		this.transaction = transaction;
	}

	public WorkItem addOrUpdateWorkItem(WorkItem workItem) {
		return workItemRepository.save(workItem);
	}

	public WorkItem updateWorkItemStatus(WorkItem workItem, WorkItem.Status status) {
		if (status == Status.ARCHIVED) {
			return transaction.execute(() -> {
				Collection<Issue> issues = serviceManager.getIssueService().getByWorkItem(workItem);
				issues.forEach(i -> i.getUpdater(serviceManager.getIssueService()).setOpenIssue(false));
				issues.forEach(i -> serviceManager.getIssueService().addOrUpdate(i));
				return workItem.getUpdater(this).setStatus(status);
			});

		} else {
			return workItem.getUpdater(this).setStatus(status);
		}
	}

	public WorkItem addUserToWorkItem(WorkItem workItem, User user) throws ServiceException {
		if (user.isActiveUser() && isValidAmountOfWorkItems(user)) {
			return workItem.getUpdater(this).setUser(user);
		} else {
			throw new ServiceException("user must be active and cannot have more than 5 work items");
		}
	}

	public Collection<WorkItem> getWorkItemsByStatus(WorkItem.Status status) {
		return workItemRepository.findByStatus(status);
	}

	public Collection<WorkItem> getAllWorkItemsByTeam(Long teamId) {
		return workItemRepository.getWorkItemsByTeamId(teamId);
	}

	public Collection<WorkItem> getAllWorkItemsByUser(User user) {
		return workItemRepository.findByUserId(user.getId());
	}

	public Collection<WorkItem> getAllWorkItemsWithIssues() {
		return workItemRepository.getAllWorkItemsWithIssues();
	}

	public Collection<WorkItem> getWorkItemByDescripton(String description) {
		return workItemRepository.findByDescription(description);
	}

	private boolean isValidAmountOfWorkItems(User user) {
		return workItemRepository.countByUserId(user.getId()) < 5;
	}

}
