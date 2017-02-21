package se.jdr.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceManager {
	
	private UserService userService;
	private WorkItemService workItemService;
	private IssueService issueService;
	private TeamService teamService;
	private AuditingService auditingService;
	private ArrayList<BaseService<?,?>> services = new ArrayList<>();
	
	@Autowired
	public ServiceManager(UserService userService, WorkItemService workItemService, IssueService issueService,
			TeamService teamService, AuditingService auditingService) {
		this.userService = userService;
		this.workItemService = workItemService;
		this.issueService = issueService;
		this.teamService = teamService;
		this.auditingService = auditingService;
		services.add(userService);
		services.add(issueService);
		services.add(teamService);
		services.add(workItemService);
		init();
	}
	
	private void init() {
		services.forEach(s -> {
			s.setUserService(userService);
			s.setWorkItemService(workItemService);
			s.setIssueService(issueService);
			s.setTeamService(teamService);
			s.setAuditingService(auditingService);
			}
		);
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
	
}
