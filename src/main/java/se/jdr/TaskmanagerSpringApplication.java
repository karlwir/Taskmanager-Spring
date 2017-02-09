package se.jdr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import se.jdr.model.Issue;
import se.jdr.model.WorkItem;
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
			UserService service = context.getBean(UserService.class);
			TeamService teamService = context.getBean(TeamService.class);
			IssueService issueService = context.getBean(IssueService.class);
			// User user = service.addOrUpdateUser(new User("joats", "joakim",
			// "holmgren", "14", true));
			// User user2 = service.addOrUpdateUser(new User("danne", "Daniel",
			// "kemter", "15", true));

			// Team team = teamService.addOrUpdateTeam(new Team("team", true));
			WorkItemService workItemService = context.getBean(WorkItemService.class);

			WorkItem workItem = workItemService.addOrUpdateWorkItem(new WorkItem("work", "item"));
			issueService.addOrUpdate(new Issue(workItem, "issue", true));

			workItemService.getAllWorkItemsWithIssues().forEach(System.out::println);
			//
			// teamService.addUserToTeam(user, team);
			// teamService.addUserToTeam(user2, team);
			//
			// workItemService.addUserToWorkItem(new WorkItem("work", "item"),
			// user2);
			// workItemService.addUserToWorkItem(new WorkItem("work", "item"),
			// user);
			// workItemService.addUserToWorkItem(new WorkItem("work2", "item2"),
			// user2);
			// workItemService.addUserToWorkItem(new WorkItem("work2", "item2"),
			// user);

			// workItemService.getAllWorkItemsByUser(user.getId()).forEach(System.out::println);

			workItemService.getWorkItemByDescripton("item2").forEach(System.out::println);

			// workItemService.getAllWorkItemsByTeam(team.getId()).forEach(System.out::println);;
		};
	}
}
