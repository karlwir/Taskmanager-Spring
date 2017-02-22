package se.jdr.service;

import java.time.LocalDateTime;
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
	public WorkItemService(WorkItemRepository wr, ServiceTransaction st) {
		super(wr, st);
	}

	public WorkItem createWorkItem(String title, String description) throws ServiceException {
		return execute(() -> repository.save(new WorkItem(title, description)));
	}

	public WorkItem updateTitle(WorkItem workItem, String title) throws ServiceException {
		return execute(() -> {
			workItem.setTitle(title);
			return repository.save(workItem);
		});
	}

	public WorkItem updateDescription(WorkItem workItem, String description) throws ServiceException {
		return execute(() -> {
			workItem.setDescription(description);
			return repository.save(workItem);
		});
	}

	public WorkItem updateStatus(WorkItem workItem, WorkItem.Status status) throws ServiceException {
		return transaction(() -> {
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
			return execute(() -> {
				workItem.setUser(user);
				return repository.save(workItem);
			});
		} else {
			throw new ServiceException("user must be active and cannot have more than 5 work items");
		}
	}

	public Collection<WorkItem> getByStatus(WorkItem.Status status) throws ServiceException {
		return execute(() -> repository.findByStatus(status));
	}

	public Collection<WorkItem> getByTeam(Long teamId) throws ServiceException {
		return execute(() -> repository.getWorkItemsByTeamId(teamId));
	}

	public Collection<WorkItem> getByUser(User user) throws ServiceException {
		return execute(() -> repository.findByUserId(user.getId()));
	}

	public Collection<WorkItem> getWithIssues() throws ServiceException {
		return execute(() -> repository.getAllWorkItemsWithIssues());
	}
	
	public Collection<WorkItem> getByDescription(String description) throws ServiceException {
		return execute(() -> repository.findByDescription(description));
	}

	public Collection<WorkItem> getByTitle(String title) throws ServiceException {
		return execute(() -> repository.findByDescription(title));
	}
	
	public Collection<WorkItem> getDoneWorkItemsByDate(LocalDateTime from, LocalDateTime to) throws ServiceException {
		return (Collection<WorkItem>) execute(() -> repository.findAll(auditingService.getDoneWorkItemsByDate(from, to)));
	}

	private boolean isValidAmountOfWorkItems(User user) throws ServiceException {
		return execute(() -> repository.countByUserId(user.getId()) < 5);
	}

}
