package se.jdr;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.service.IssueService;
import se.jdr.service.ServiceManager;
import se.jdr.service.TeamService;
import se.jdr.service.UserService;
import se.jdr.service.WorkItemService;

@SpringBootApplication
public class Taskmanager {

	public static void main(String[] args) {
		SpringApplication.run(Taskmanager.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			
			ServiceManager serviceManager = context.getBean(ServiceManager.class);
			
			UserService userService = serviceManager.getUserService();
			TeamService teamService = serviceManager.getTeamService();
			IssueService issueService = serviceManager.getIssueService();
			WorkItemService workItemService = serviceManager.getWorkItemService();

//			User user = userService.createUser("Danneusername2", "Daniel", "kemter");
			
//			System.out.println(auditingService.getByNameUpdate("kemter", LocalDateTime.now().minusHours(10), LocalDateTime.now()));
//
//			userService.updateFirstName(1L, "Karl");
			
//			System.out.println(userService.getRevisionNameUpdate("Daniel", LocalDateTime.now().minusHours(10), LocalDateTime.now()));
			
//			 Team team = teamService.createTeam("teammmmmmmmmmmm");
//			 teamService.addUserToTeam(user, team);
////
//			WorkItem workItem = workItemService.createWorkItem("workitem1", "workitem1 description");
//			workItemService.createWorkItem("workitem2", "workitem2 description");
//			workItemService.createWorkItem("workitem3", "workitem3 description");


			//			workItemService.updateTitle(workItemService.getById(8L), "newtitle5");
			workItemService.updateStatus(workItemService.getById(4L), Status.UNSTARTED);
			workItemService.updateStatus(workItemService.getById(4L), Status.DONE);
			
			System.out.println(
					workItemService.getDoneWorkItemsByDate(LocalDateTime.now().minusSeconds(2), LocalDateTime.now().plusSeconds(1))
			);

		};
	}
}
