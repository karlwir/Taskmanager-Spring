package se.jdr.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.domain.Page;

import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.service.ServiceException;
import se.jdr.service.ServiceManager;
import se.jdr.service.UserService;
import se.jdr.service.WorkItemService;

public class TerminalUI {

	private Scanner scanner;
	private String input;
	private User selectedUser = null;
	private Collection<User> userCollection;
	private Page<User> userPage;
	private boolean viewNextPage = false;
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
	
	public void mainMenu(ServiceManager serviceManager, User auditor) throws ServiceException {
		boolean exit = false;

		print("\nLogged in as " + ANSI_CYAN + auditor.getFirstname() + " " + auditor.getLastname() + ANSI_RESET
				+ ". You have " + ANSI_CYAN + serviceManager.getWorkItemService().getByUser(auditor).size()
				+ ANSI_RESET + " assigned workitems.");
		
		while (!exit) {
			printSelected();

			switch (inputString("\n" + "Main menu - Choose data type: \n " + ANSI_CYAN
					+ " (1) User, (2) Workitem, (0) Deselect all, (logout) Logout" + ANSI_RESET)) {
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
			case "logout":
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
			switch (inputString("\n" + "Workitem - Choose action: \n " + ANSI_CYAN
					+ " (1) Add Workitem, (2) Find Workitem, (3) Update Workitem, (4) View revisions, (back) Back " + ANSI_RESET)) {
			case "1":
				print("\n" + "Add Workitem:");
				selectedWorkItem = workItemService.createWorkItem(inputString("Title: "), inputString("Description: "));
				break;
			case "2":
				switch (inputString("\n" + "Find Workitem: \n  " + ANSI_CYAN
						+ " (1) By title, (2) By description, (3) By User, (4) DONE past hour, (back) Back"
						+ ANSI_RESET)) {
				case "1":
					workItemCollection = workItemService.getByTitle(inputString("Title: "));
					selectedWorkItem = selectFromCollection(workItemCollection);
					break;
				case "2":
					workItemCollection = workItemService.getByDescription(inputString("Description: "));
					selectedWorkItem = selectFromCollection(workItemCollection);
					break;
				case "3":
					if (selectedUser == null) {
						print("No user selected");
						break;
					}
					workItemCollection = workItemService.getByUser(selectedUser);
					selectedWorkItem = selectFromCollection(workItemCollection);
					break;
				case "4":
					workItemCollection = workItemService.getDoneWorkItemsByDate(LocalDateTime.now().minusHours(1),
							LocalDateTime.now());
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
				if (selectedWorkItem == null) {
					print("No Workitem selected for update");
					break;
				}
				switch (inputString("\n" + "Update Workitem: \n " + ANSI_CYAN
						+ " (1) Update title, (2) Update description, (3) Update status DONE, (4) Assign User, (back) Back"
						+ ANSI_RESET)) {
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
					if (selectedUser == null) {
						print("No user selected");
						break;
					}
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
			case "4":
				if (selectedWorkItem == null) {
					print("No WorkItem selected for revisions");
					break;
				}
				workItemCollection = workItemService.getRevisions(selectedWorkItem);
				selectFromCollection(workItemCollection);
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
			switch (inputString("\n" + "User - Choose action: \n " + ANSI_CYAN
					+ " (1) Add User, (2) Find User, (3) Update User, (4) View revisions (back) Back " + ANSI_RESET)) {
			case "1":
				print("\n" + "Add user:");
				selectedUser = userService.createUser(inputString("Firstname: "), inputString("Lastname: "),
						inputString("Password: "));
				break;
			case "2":
				switch (inputString("\n" + "Find User: \n" + ANSI_CYAN
						+ " (1) By firstname, (2) By lastname, (3) By username, (4) Get all" + ANSI_RESET)) {
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
				case "4":
					viewNextPage = false;
					userPage = userService.getAll(0);
					selectedUser = selectFromPage(userPage);
					while(viewNextPage) {
						Page<User> previous = userPage;
						userPage = userService.getAll(previous.nextPageable());
						viewNextPage = false;
						selectedUser = selectFromPage(userPage);
					}
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
				if (selectedUser == null) {
					print("No user selected for update");
					break;
				}
				switch (inputString("\n" + "Update User: \n" + ANSI_CYAN
						+ "(1) Update firstname, (2) Update lastname, (3) Inactivate User, (4) Activate User, (5) Update username, (back) Back"
						+ ANSI_RESET)) {
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
				case "5":
					userService.updateUsername(selectedUser);
					break;
				case "back":
					exit = true;
					break;
				default:
					print("Invalid command");
					break;
				}
				break;
			case "4":
				if (selectedUser == null) {
					print("No user selected for revisions");
					break;
				}
				userCollection = userService.getRevisions(selectedUser);
				selectFromCollection(userCollection);
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

	private <T> T selectFromPage(Page<T> page) {
		if (page.getSize() == 0) {
			print("No result");
			return null;
		}
		List<T> list = page.getContent();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(ANSI_CYAN + "(" + (i + 1) + ")" + ANSI_PURPLE + " " + list.get(i) + ANSI_RESET);
		}
		if(page.hasNext()){
			print("Select with " + ANSI_CYAN + "(#)" + ANSI_RESET + " Exit with " + ANSI_CYAN + "(0)" + ANSI_RESET + " Next page with " + ANSI_CYAN + "(11)" + ANSI_RESET);			
		} else {
			print("Select with " + ANSI_CYAN + "(#)" + ANSI_RESET + " Exit with " + ANSI_CYAN + "(0)" + ANSI_RESET);			
		}
		scanner = new Scanner(System.in);
		int command = scanner.nextInt();
		if (command == 11 && page.hasNext()) {
			viewNextPage = true;
			return null;
		} else if(command > list.size() || command == 0) {
			return null;
		} else {
			return list.get(command - 1);			
		}
	}

	private <T> T selectFromCollection(Collection<T> collection) {
		if (collection.size() == 0) {
			print("No result");
			return null;
		}
		ArrayList<T> list = new ArrayList<>();
		list.addAll(collection);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(ANSI_CYAN + "(" + (i + 1) + ")" + ANSI_PURPLE + " " + list.get(i) + ANSI_RESET);
		}
		print("Select with " + ANSI_CYAN + "(#)" + ANSI_RESET + " Exit with " + ANSI_CYAN + "(0)" + ANSI_RESET);
		scanner = new Scanner(System.in);
		int command = scanner.nextInt();
		if (command > list.size() || command == 0) {
			return null;
		}
		return list.get(command - 1);
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
		if (selectedUser != null) {
			print("* User " + selectedUser.getUsername().toString() + " is selected.");
		}
		if (selectedWorkItem != null) {
			print("* Workitem '" + selectedWorkItem.getTitle().toString() + "' is selected.");
		}
	}
}
