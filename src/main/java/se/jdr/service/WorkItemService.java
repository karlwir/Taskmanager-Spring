package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Issue;
import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.repository.UserRepository;
import se.jdr.repository.WorkItemRepository;

@Component
public final class WorkItemService {

	private final WorkItemRepository workItemRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public WorkItemService(WorkItemRepository workItemRepository, UserRepository userRepository) {
		this.workItemRepository = workItemRepository;
		this.userRepository = userRepository;
	}
	
	public WorkItem addOrUpdateWorkItem(WorkItem workItem) {
		return workItemRepository.save(workItem);
	}
	
	public WorkItem updateWorkItemStatus(WorkItem workItem, WorkItem.Status status) {
		workItem.setStatus(status);
		return addOrUpdateWorkItem(workItem);
	}
	
	public void removeWorkItem(WorkItem workItem) {
		workItemRepository.delete(workItem);
	}
	
	public void addUserToWorkItem(WorkItem workItem, User user) {
		User userToDB = userRepository.save(user);
		workItem.setUser(userToDB);
		addOrUpdateWorkItem(workItem);
	}
	
	public Collection<WorkItem> getWorkItemsByStatus(WorkItem.Status status) {
		return workItemRepository.findByStatus(status);
	}
	
//	public Collection<WorkItem> getAllWorkItemsByTeam(Long teamId) {
//		return workItemRepository.getWorkItemsByTeamId(teamId);
//	}
	
	public Collection<WorkItem> getAllWorkItemsByUser(Long userId) {
		return workItemRepository.findByUserId(userId);
	}
	
	public Collection<WorkItem> getAllWorkItemsWithIssues() {
		return workItemRepository.getAllWorkItemsByIssue();
	}
	
	public Collection<WorkItem> getWorkItemByDescripton(String description) {
		return workItemRepository.findByDescriptionLike(description);
	}
		
	}
