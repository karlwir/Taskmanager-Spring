package se.jdr;

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
import se.jdr.service.TeamService;
import se.jdr.service.UserService;
import se.jdr.service.WorkItemService;

@SpringBootApplication
public class TaskmanagerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			UserService userService = context.getBean(UserService.class);
			TeamService teamService = context.getBean(TeamService.class);
			IssueService issueService = context.getBean(IssueService.class);
			WorkItemService workItemService = context.getBean(WorkItemService.class);

			// .addOrUpdateUser(new User("joats", "joakim", "holmgren",
			// "1333333333333333333333", true));
			User user2 = userService.addOrUpdateUser(new User("danne101010101", "Daniel", "kemter", "15", true));
			// User user3 = userService.addOrUpdateUser(new User("danneE",
			// "Daniel", "kemter", "199", true));

			Team team = teamService.addOrUpdateTeam(new Team("teammmmmmmmmmmm", true));
			// teamService.addUserToTeam(user, team);
			teamService.addUserToTeam(user2, team);
			// teamService.addUserToTeam(user3, team);

			WorkItem workItem = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
			WorkItem workItem2 = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
			WorkItem workItem3 = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
			workItemService.updateWorkItemStatus(workItem, Status.DONE);
			//
			// issueService.addIssue(workItem, "description");
			// issueService.addIssue(workItem, "description2");
			//
			// System.out.println();
			// workItemService.getAllWorkItemsWithIssues().forEach(System.out::println);
			// //
			// teamService.addUserToTeam(user, team);
			// teamService.addUserToTeam(user2, team);
			userService.updateUserStatus(user2, false);
			//
			workItemService.addUserToWorkItem(workItem, user2);
			workItemService.addUserToWorkItem(workItem2, user2);
			workItemService.addUserToWorkItem(workItem3, user2);
			System.out.println(userService.getUserByUserId("15"));
			// workItemService.addUserToWorkItem(new WorkItem("work", "item"),
			// user);
			// workItemService.addUserToWorkItem(new WorkItem("work2", "item2"),
			// user2);
			// workItemService.addUserToWorkItem(new WorkItem("work2", "item2"),
			// user);

			// workItemService.getAllWorkItemsByUser(user.getId()).forEach(System.out::println);

			// workItemService.getWorkItemByDescripton("item2").forEach(System.out::println);

			// workItemService.getAllWorkItemsByTeam(team.getId()).forEach(System.out::println);
			;
		};
	}
}
