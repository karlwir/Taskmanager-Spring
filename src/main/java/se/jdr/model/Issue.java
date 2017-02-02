package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue extends AbstractEntity {

	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private boolean openIssue;
	@Column(nullable = false)
	private long workitemId;

	protected Issue() {
	}

	public Issue(Workitem workitem, String description, boolean openIssue) {
		this.description = description;
		this.openIssue = openIssue;
		workitemId = workitem.getId();
	}

	public String getDescription() {
		return description;
	}

	public long getWorkitemId() {
		return workitemId;
	}

	public boolean isOpenIssue() {
		return openIssue;
	}

	@Override
	public String toString() {
		return "Issue " + super.getId() + ", description: " + description + ", is open: " + openIssue
				+ ", workitem id: " + workitemId;
	}

}
