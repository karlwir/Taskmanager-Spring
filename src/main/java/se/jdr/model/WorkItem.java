package se.jdr.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Audited(withModifiedFlag=true)
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
	
	@OneToMany(mappedBy = "workItem")
	@NotAudited
	private Collection<Issue> issues;

	@LastModifiedDate
	protected LocalDateTime revisionDate;
	@LastModifiedBy
	@OneToOne
	protected User revisionBy;

	protected WorkItem() {
	}

	public WorkItem(String title, String description) {
		this.title = title;
		this.description = description;
		this.status = Status.UNSTARTED;
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
	
	public boolean hasAssignUser() {
		return user != null;
	}

	public Collection<Issue> getIssues() {
		return issues;
	}

	public enum Status {
		DONE, UNSTARTED, STARTED, ARCHIVED
	}
	
	private boolean hasCreatedBy(){
		return createdBy != null;
	}
	
	private boolean hasRevisionBy() {
		return revisionBy != null;
	}

	@Override
	public String toString() {
		String assignedUser = hasAssignUser() ? user.getUsername() : null;
		String createdBy = hasCreatedBy() ? this.createdBy.getUsername() : null;
		String revisionBy = hasRevisionBy() ? this.revisionBy.getUsername() : null;
		return "Workitem, title: " + title 
						+ ", description: " + description 
						+ ", status: " + status 
						+ ", assignedUserId: " + assignedUser 
						+ ", createdBy: " + createdBy + " - " + createdDate 
						+ ", revisionBy: " + revisionBy + " - " + revisionDate;
	}

	public WorkItem setStatus(Status status) {
		this.status = status;
		return this;
	}

	public WorkItem setUser(User user) {
		this.user = user;
		return this;
	}

	public WorkItem setTitle(String title) {
		this.title = title;
		return this;
	}

	public WorkItem setDescription(String description) {
		this.description = description;
		return this;
	}
		
}
