package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_items")
public class Workitem {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private int assignedUserId;
	private String dateOfCompletion;

	protected Workitem() {
	}

	public Workitem(String title, String description, String status, int assignedUserId, String dateOfCompletion) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.assignedUserId = assignedUserId;
		this.dateOfCompletion = dateOfCompletion;
	}

	public Long getId() {
		return id;
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

	@Override
	public String toString() {
		return "Workitem " + id + ", title: " + title + ", description: " + description + ", status: " + status
				+ ", assignedUserId: " + assignedUserId + ", dateOfCompletion: " + dateOfCompletion;
	}

}
