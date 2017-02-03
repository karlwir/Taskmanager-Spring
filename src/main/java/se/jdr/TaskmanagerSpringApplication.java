package se.jdr;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.model.Workitem;
import se.jdr.repository.TeamRepository;
import se.jdr.repository.UserRepository;
import se.jdr.repository.WorkitemRepository;
import se.jdr.service.UserService;

@SpringBootApplication
public class TaskmanagerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			UserService service = context.getBean(UserService.class);
//			service.addOrUpdateUser(new User("121", "firstname", "lastname", "12", true));
//			service.addOrUpdateUser(new User("122", "firstname", "lastname", "13", true));
			User user = service.addOrUpdateUser(new User("joats", "joakim", "holmgren", "14", true));
			
			TeamRepository teamRepository = context.getBean(TeamRepository.class);
			
			Team team = teamRepository.save(new Team("team", true));
			Collection<User> users = new ArrayList();
			users.add(user);
			team.setUsers(users);
			teamRepository.save(team);
			
			System.out.println(team.toString());
			
			System.out.println(service.getUserByLastname("la"));
			
			
		};
	}	
}
