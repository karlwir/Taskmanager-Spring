package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue extends AbstractEntity {

	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private boolean openIssue;
	@ManyToOne
	private WorkItem workItem;

	protected Issue() {
	}

	public Issue(WorkItem workitem, String description, boolean openIssue) {
		this.description = description;
		this.openIssue = openIssue;
		this.workItem = workitem;
	}

	public String getDescription() {
		return description;
	}

	public boolean isOpenIssue() {
		return openIssue;
	}

	public WorkItem getWorkitem() {
		return workItem;
	}

	@Override
	public String toString() {
		return "Issue " + getId() + ", description: " + description + ", is open: " + openIssue + ", workitem id: "
				+ workItem;
	}

}
