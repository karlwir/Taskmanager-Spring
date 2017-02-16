package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import se.jdr.service.IssueService;

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

	public Issue(WorkItem workitem, String description) {
		this.description = description;
		this.openIssue = true;
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
	public IssueUpdater getUpdater(IssueService issueService) {
		return new IssueUpdater(this, issueService);
	}

	public class IssueUpdater {

		private Issue issue;
		private IssueService issueService;

		private IssueUpdater(Issue issue, IssueService issueService) {
			this.issue = issue;
			this.issueService = issueService;
		}

		public Issue setDescription(String description) {
			issue.description = description;
			return issueService.addOrUpdate(issue);
		}

		public Issue setOpenIssue(boolean openIssue) {
			issue.openIssue = openIssue;
			return issueService.addOrUpdate(issue);
		}

	}

}
