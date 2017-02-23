package se.jdr.service;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import se.jdr.model.User;

@Component
public class LoginService implements AuditorAware<User> {

	private User user;
	private Scanner input;
	private String username;
	private String password;
	private ApplicationContext context;

	@Autowired
	public LoginService(ApplicationContext context) {
		this.context = context;
	}

	public ServiceManager login() {
		user = null;
		ServiceManager serviceManager = context.getBean(ServiceManager.class);
		System.out.println("Enter username: ");
		while (user == null) {
			input = new Scanner(System.in);
			username = input.nextLine();
			try {
				user = serviceManager.getUserService().getByUsername(username);
				if(user == null) {
					throw new ServiceException("Invalid username, try again: ");
				}
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Enter password: ");
		while (true) {
			input = new Scanner(System.in);
			password = input.nextLine();
			try {
				if(BCrypt.checkpw(password, user.getPasswordHash())) {
					break;
				} else {
					throw new ServiceException("Wrong password, try again: ");
				}
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
			} finally {			
			}
		}
//		input.close();	
		return serviceManager;
	}

	@Override
	public User getCurrentAuditor() {
		return user;
	}

}
