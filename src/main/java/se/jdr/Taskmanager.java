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
import se.jdr.repository.WorkItemRepository;
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
			
			serviceManager.setIssueService(context.getBean(IssueService.class));
			serviceManager.setTeamService(context.getBean(TeamService.class));
			serviceManager.setUserService(context.getBean(UserService.class));
			serviceManager.setWorkItemService(context.getBean(WorkItemService.class));
			
			UserService userService = serviceManager.getUserService();
			TeamService teamService = serviceManager.getTeamService();
			IssueService issueService = serviceManager.getIssueService();
			WorkItemService workItemService = serviceManager.getWorkItemService();

			userService.init();
			teamService.init();
			issueService.init();
			workItemService.init();

//			User user = userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//			userService.createUser(LocalDateTime.now().toString(), "Daniel", "kemter");
//
//			 Team team = teamService.createTeam("teammmmmmmmmmmm");
//			 teamService.addUserToTeam(user, team);
			 
			 userService.getAll(5, 10).getContent().forEach(System.out::println);
			// teamService.addUserToTeam(user2, team);
			// teamService.addUserToTeam(user3, team);
////
//			WorkItem workItem = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
//			WorkItem workItem2 = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
//			WorkItem workItem3 = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
//			WorkItem workItem4 = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
//
//			workItemService.addUserToWorkItem(workItem, user);
//			workItemService.addUserToWorkItem(workItem2, user);
//			workItemService.addUserToWorkItem(workItem3, user);
//			workItemService.addUserToWorkItem(workItem4, user);
//
//			 userService.updateUserStatus(user, false);

			// Collection<Issue> issues =
			// issueService.getByWorkItemId(workItem.getId());

			// issues.forEach(System.out::println);

			// WorkItem workItem2 = workItemService.addOrUpdateWorkItem(new
			// WorkItem("work", "item"));
			// WorkItem workItem3 = workItemService.addOrUpdateWorkItem(new
			// WorkItem("work", "item"));

//			workItemService.updateWorkItemStatus(workItem, Status.ARCHIVED);

			//
			// issueService.addIssue(workItem, "description");
			// issueService.addIssue(workItem, "description2");
			// System.out.println();
//			workItemService.getAllWorkItemsWithIssues().forEach(System.out::println);
			// teamService.addUserToTeam(user, team);
			// teamService.addUserToTeam(user2, team);
			// userService.updateUserStatus(user2, false);
			//
			// workItemService.addUserToWorkItem(workItem2, user2);
			// workItemService.addUserToWorkItem(workItem3, user2);
			// System.out.println(userService.getUserByUserId("15"));
			// workItemService.addUserToWorkItem(new WorkItem("work", "item"),
			// user);
			// workItemService.addUserToWorkItem(new WorkItem("work2", "item2"),
			// user2);
			// workItemService.addUserToWorkItem(new WorkItem("work2", "item2"),
			// user);

			// workItemService.getAllWorkItemsByUser(user.getId()).forEach(System.out::println);

			// workItemService.getWorkItemByDescripton("item2").forEach(System.out::println);

		};
	}
}
