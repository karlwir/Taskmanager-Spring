package se.jdr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import se.jdr.model.User;
import se.jdr.model.Workitem;
import se.jdr.repository.UserrRepository;
import se.jdr.repository.WorkitemRepository;

@SpringBootApplication
public class TaskmanagerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			UserrRepository repo = context.getBean(UserrRepository.class);
			WorkitemRepository workrepo = context.getBean(WorkitemRepository.class);

			Workitem workitem = workrepo.save(new Workitem("title", "description"));
			User user = repo.save(new User("121", "firstname", "lastname", true));
			System.out.println(workitem);

			workitem.setDateOfCompletion("2014");
			workrepo.save(workitem);

			System.out.println(user);
			System.out.println(workitem);
		};
	}
}
