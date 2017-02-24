package se.jdr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import se.jdr.model.User;
import se.jdr.service.LoginService;
import se.jdr.service.ServiceManager;
import se.jdr.utils.TerminalUI;

@SpringBootApplication
public class Taskmanager {

	private User auditor;
	
	public static void main(String[] args) {
		SpringApplication.run(Taskmanager.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			while (true) {
				LoginService loginService = context.getBean(LoginService.class);
				ServiceManager serviceManager = loginService.login();
				auditor = loginService.getCurrentAuditor();
				new TerminalUI().mainMenu(serviceManager, auditor);
				serviceManager = null;
			}
		};
	}

}
