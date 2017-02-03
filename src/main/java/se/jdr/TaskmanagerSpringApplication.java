package se.jdr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import se.jdr.model.User;
import se.jdr.model.Workitem;
import se.jdr.repository.UserRepository;
import se.jdr.repository.WorkitemRepository;

@SpringBootApplication
public class TaskmanagerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			UserRepository repo = context.getBean(UserRepository.class);
			WorkitemRepository workrepo = context.getBean(WorkitemRepository.class);

			Workitem workitem = workrepo.save(new Workitem("title", "description"));
			System.out.println(workitem);
			User user = new User("1212", "firstname", "lastname", "id?", true);
			repo.save(user);

			workitem.setDateOfCompletion("2014");
			workrepo.save(workitem);

			System.out.println(user);
			System.out.println(workitem);
		};
	}
}
