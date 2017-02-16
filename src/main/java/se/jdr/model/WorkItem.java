package se.jdr.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import se.jdr.service.WorkItemService;

@Entity
@Table(name = "workitems")
public class WorkItem extends AbstractEntity {

	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private User user;
	private String dateOfCompletion;
	@OneToMany(mappedBy = "workItem", fetch = FetchType.EAGER)
	private Collection<Issue> issues;

	protected WorkItem() {
	}

	public WorkItem(String title, String description) {
		this.title = title;
		this.description = description;
		this.status = Status.STARTED;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public User getUser() {
		return user;
	}

	public String getDateOfCompletion() {
		return dateOfCompletion;
	}

	public Collection<Issue> getIssues() {
		return issues;
	}

	public void setDateOfCompletion(String dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public enum Status {
		DONE, UNSTARTED, STARTED, ARCHIVED
	}

	@Override
	public String toString() {
		return "Workitem " + getId() + ", title: " + title + ", description: " + description + ", status: " + status
				+ ", assignedUserId: " + user + ", dateOfCompletion: " + dateOfCompletion;
	}

	public WorkItemUpdater getUpdater(WorkItemService workItemService) {
		return new WorkItemUpdater(this, workItemService);
	}
	
	public class WorkItemUpdater {
		
		private WorkItem workItem;
		private WorkItemService workItemService;
		
		private WorkItemUpdater(WorkItem workitem, WorkItemService workItemService) {
			this.workItem = workitem;
			this.workItemService = workItemService;
		}

		public WorkItem setStatus(Status status) {
			workItem.status = status;
			return workItemService.addOrUpdateWorkItem(workItem);
		}

		public WorkItem setUser(User user) {
			workItem.user = user;
			return workItemService.addOrUpdateWorkItem(workItem);
		}
		
	}

}
