package se.jdr.service;

import org.springframework.stereotype.Component;

@Component
public class ServiceManager {
	
	private UserService userService;
	private WorkItemService workItemService;
	private IssueService issueService;
	private TeamService teamService;
	private AuditingService auditingService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setWorkItemService(WorkItemService workItemService) {
		this.workItemService = workItemService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public UserService getUserService() {
		return userService;
	}

	public WorkItemService getWorkItemService() {
		return workItemService;
	}

	public IssueService getIssueService() {
		return issueService;
	}

	public TeamService getTeamService() {
		return teamService;
	}

	public AuditingService getAuditingService() {
		return auditingService;
	}

	public void setAuditingService(AuditingService auditingService) {
		this.auditingService = auditingService;
	}
	
}
