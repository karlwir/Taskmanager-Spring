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
public final class WorkItemService extends BaseService<WorkItem, WorkItemRepository> {

	@Autowired
	public WorkItemService(WorkItemRepository wr, ServiceManager sm, ServiceTransaction st) {
		super(wr, sm, st);
	}

	public WorkItem createWorkItem(String title, String description) throws ServiceException {
		return super.execute(() -> repository.save(new WorkItem(title, description)));
	}

	public WorkItem updateTitle(WorkItem workItem, String title) throws ServiceException {
		return super.execute(() -> {
			workItem.setTitle(title);
			return repository.save(workItem);
		});
	}

	public WorkItem updateDescription(WorkItem workItem, String description) throws ServiceException {
		return super.execute(() -> {
			workItem.setDescription(description);
			return repository.save(workItem);
		});
	}

	public WorkItem updateStatus(WorkItem workItem, WorkItem.Status status) throws ServiceException {
		return super.transaction(() -> {
			if (status == Status.ARCHIVED) {
				for(Issue issue : issueService.getByWorkItem(workItem)) {
					issueService.updateStatusClosed(issue);
				}
			}
			workItem.setStatus(status);
			return repository.save(workItem);
		});
	}

	public WorkItem addUserToWorkItem(WorkItem workItem, User user) throws ServiceException {
		if (user.isActiveUser() && isValidAmountOfWorkItems(user)) {
			return super.execute(() -> {
				workItem.setUser(user);
				return repository.save(workItem);
			});
		} else {
			throw new ServiceException("user must be active and cannot have more than 5 work items");
		}
	}

	public Collection<WorkItem> getWorkItemsByStatus(WorkItem.Status status) throws ServiceException {
		return super.execute(() -> repository.findByStatus(status));
	}

	public Collection<WorkItem> getWorkItemsByTeam(Long teamId) throws ServiceException {
		return super.execute(() -> repository.getWorkItemsByTeamId(teamId));
	}

	public Collection<WorkItem> getWorkItemsByUser(User user) throws ServiceException {
		return super.execute(() -> repository.findByUserId(user.getId()));
	}

	public Collection<WorkItem> getWorkItemsWithIssues() throws ServiceException {
		return super.execute(() -> repository.getAllWorkItemsWithIssues());
	}

	public Collection<WorkItem> getWorkItemByDescripton(String description) throws ServiceException {
		return super.execute(() -> repository.findByDescription(description));
	}

	private boolean isValidAmountOfWorkItems(User user) throws ServiceException {
		return super.execute(() -> repository.countByUserId(user.getId()) < 5);
	}

}
