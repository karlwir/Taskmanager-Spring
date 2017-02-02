package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "work_items")
public class Workitem extends AbstractEntity {

	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private int assignedUserId;
	private String dateOfCompletion;
	private Issue issue;

	protected Workitem() {
	}

	public Workitem(String title, String description, String status, int assignedUserId, String dateOfCompletion) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.assignedUserId = assignedUserId;
		this.dateOfCompletion = dateOfCompletion;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getStatus() {
		return status;
	}

	public int getAssignedUserId() {
		return assignedUserId;
	}

	public String getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	@Override
	public String toString() {
		return "Workitem " + super.getId() + ", title: " + title + ", description: " + description + ", status: "
				+ status + ", assignedUserId: " + assignedUserId + ", dateOfCompletion: " + dateOfCompletion;
	}

}
