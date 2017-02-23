package se.jdr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.service.LoginService;
import se.jdr.service.ServiceException;
import se.jdr.service.ServiceManager;
import se.jdr.service.UserService;
import se.jdr.service.WorkItemService;

@SpringBootApplication
public class Taskmanager {
	
	private Scanner scanner;
	private String input;
	private User selectedUser = null;
	private Collection<User> userCollection;
	private WorkItem selectedWorkItem = null;
	private Collection<WorkItem> workItemCollection;


	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void main(String[] args) {
		SpringApplication.run(Taskmanager.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			print(" _______ _______ _______ __  __ _______\n|_     _|   _   |     __|  |/  |   |   | _____ _____ _____ _____ _____ ____\n  |   | |       |__     |     <|       ||  _  |     |  _  |  _  |  -__|   _|\n  |___| |___|___|_______|__|\\__|__|_|__||___._|__|__|___._|___  |_____|__|\n                                                          |_____|");
			LoginService loginService = context.getBean(LoginService.class);
			
			ServiceManager serviceManager = loginService.login();
			
			mainMenu(serviceManager);
		};
	}
	
	private void mainMenu(ServiceManager serviceManager) throws ServiceException{
		boolean exit = false;
		
		while (!exit) {
			printSelected();
			
			switch (inputString("Main menu - Choose data type: \n " + ANSI_CYAN + " (1) User, (2) Workitem, (0) Deselect all, (exit) Exit" + ANSI_RESET)) {
			case "1":
				userMenu(serviceManager.getUserService());
				break;
			case "2":
				workItemMenu(serviceManager.getWorkItemService());
				break;
			case "0":
				selectedUser = null;
				selectedWorkItem = null;
				break;
			case "exit":
				exit = true;
				break;
			default:
				System.out.println("Invalid command");
				break;
			}
		}
	}

	private void workItemMenu(WorkItemService workItemService) throws ServiceException {
		boolean exit = false;
		
		while (!exit) {
			printSelected();
			switch (inputString("Workitem - Choose action: \n " + ANSI_CYAN + " (1) Add Workitem, (2) Find Workitem, (3) Update Workitem, (back) Back " + ANSI_RESET)) {
			case "1":
				print("Add Workitem:");
				workItemService.createWorkItem(inputString("Title: "), inputString("Description: "));
				break;
			case "2":
				switch (inputString("Find Workitem: \n (1) By title, (2) By description, (3) By User (back) Back")) {				
					case "1":
						workItemCollection = workItemService.getByTitle(inputString("Title: "));
						selectedWorkItem = selectFromCollection(workItemCollection);
						break;
					case "2":
						workItemCollection = workItemService.getByDescription(inputString("Description: "));
						selectedWorkItem = selectFromCollection(workItemCollection);
						break;
					case "3":
						if(selectedUser == null) {print("No user selected"); break; }
						workItemCollection = workItemService.getByUser(selectedUser);
						selectedWorkItem = selectFromCollection(workItemCollection);
						break;
					case "back":
						exit = true;
						break;
					default:
						print("Invalid command");
						break;
				}
				break;
			case "3":
				if(selectedWorkItem == null) {print("No Workitem selected for update"); break; }
				switch (inputString("Update Workitem: \n " + ANSI_CYAN + " (1) Update title, (2) Update description, (3) Update status DONE, (4) Assign User, (back) Back" + ANSI_RESET)) {				
					case "1":
						workItemService.updateTitle(selectedWorkItem, inputString("New Title: "));
						break;
					case "2":
						workItemService.updateDescription(selectedWorkItem, inputString("New Description: "));
						break;
					case "3":
						workItemService.updateStatus(selectedWorkItem, Status.DONE);
						break;
					case "4":
						if(selectedUser == null) {print("No user selected"); break; }
						workItemService.addUserToWorkItem(selectedWorkItem, selectedUser);
						break;
					case "back":
						exit = true;
						break;
					default:
						print("Invalid command");
						break;
				}				
			break;
			case "back":
				exit = true;
				break;
			default:
				print("Invalid command");
				break;
			}
		}		
	}

	private void userMenu(UserService userService) throws ServiceException {
		boolean exit = false;
		
		while (!exit) {
			printSelected();
			switch (inputString("User - Choose action: \n " + ANSI_CYAN + " (1) Add User, (2) Find User, (3) Update User, (back) Back " + ANSI_RESET)) {
			case "1":
				print("Add user:");
				userService.createUser(inputString("Firstname: "), inputString("Lastname: "), inputString("Password: "));
				break;
			case "2":
				switch (inputString("Find User: \n" + ANSI_CYAN + " (1) By firstname, (2) By lastname, (3) By username" + ANSI_RESET)) {				
					case "1":
						userCollection = userService.getByFirstname(inputString("Firstname: "));
						selectedUser = selectFromCollection(userCollection);
						break;
					case "2":
						userCollection = userService.getByLastname(inputString("Lastname: "));
						selectedUser = selectFromCollection(userCollection);
						break;
					case "3":
						selectedUser = userService.getByUsername(inputString("Username: "));
						break;
					case "back":
						exit = true;
						break;
					default:
						print("Invalid command");
						break;
				}
				break;
			case "3":
				if(selectedUser == null) {print("No user selected for update"); break; }
				switch (inputString("Update User: \n" + ANSI_CYAN + "(1) Update firstname, (2) Update lastname, (3) Inactivate User, (4) Activate User, (back) Back" + ANSI_RESET)) {				
					case "1":
						userService.updateFirstName(selectedUser, inputString("New firstname: "));
						break;
					case "2":
						userService.updateLastName(selectedUser, inputString("New lastname: "));
						break;
					case "3":
						userService.updateStatusInactive(selectedUser);
						break;
					case "4":
						userService.updateStatusActive(selectedUser);
						break;
					case "back":
						exit = true;
						break;
					default:
						print("Invalid command");
						break;
				}				
			break;
			case "back":
				exit = true;
				break;
			default:
				print("Invalid command");
				break;
			}
		}
	}

	private <T> T selectFromCollection(Collection<T> collection) {
		if(collection.size() == 0) {
			print("No result");
			return null;
		}
		ArrayList<T> list = new ArrayList<>();
		list.addAll(collection);
		for(int i = 0; i < list.size(); i++) {
			System.out.println(ANSI_CYAN + "(" + i + ")" + ANSI_PURPLE + " " + list.get(i) + ANSI_RESET);
		}
		print("Select with " + ANSI_CYAN + "(#)"  + ANSI_RESET +  ":");
		scanner = new Scanner(System.in);
		int command = scanner.nextInt();
		if(command > list.size()) {
			return null;
		}
		return list.get(command);
	}
	

	private String inputString(String helper) {
		print(helper);
		scanner = new Scanner(System.in);
		input = scanner.nextLine();
		return input;
	}
	
	private void print(String message) {
		System.out.println(message);
	}
	
	private void printSelected() {
		if(selectedUser != null){print("* User " + selectedUser.getUsername().toString() + " is selected.");}
		if(selectedWorkItem != null){print("* Workitem '" + selectedWorkItem.getTitle().toString() + "' is selected.");}	
	}

}
