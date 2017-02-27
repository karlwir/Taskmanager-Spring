package se.jdr.service;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import se.jdr.AbstractTest;
import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;

public class UserServiceTest extends AbstractTest {
	
	@Autowired
	private UserService userService;

	@Autowired
	private WorkItemService workItemService;
	
	private User createdUser;
	private User updateUser;	
	private String firstname = "firstname";
	private String lastname = "lastname";
	private String password = "password";

	private WorkItem createdWorkitem;
	private String title = "title";
	private String description = "description";

	@Before
	public void prepare() throws ServiceException {
		userService.setWorkItemService(workItemService);
		createdUser = userService.createUser(firstname, lastname, password);
		updateUser = userService.createUser(firstname, lastname, password);
		createdWorkitem = workItemService.createWorkItem(title, description);
	}

	@Test
	public void canGetByUsername() throws ServiceException {
		User fetchedUser = userService.getByUsername(createdUser.getUsername());
		assertEquals(createdUser, fetchedUser);
	}

	@Test
	public void canGetByFirstname() throws ServiceException {
		Collection<User> fetchedUsers = userService.getByFirstname(createdUser.getFirstname(), 0, 10);
		assertTrue(fetchedUsers.stream().anyMatch(object -> object.equals(createdUser)));
	}

	@Test
	public void canGetByLastname() throws ServiceException {
		Collection<User> fetchedUsers = userService.getByLastname(createdUser.getLastname(), 0, 10);
		assertTrue(fetchedUsers.stream().anyMatch(object -> object.equals(createdUser)));
	}
	
	@Test
	public void canGenerateUsername() throws ServiceException {
		userService.updateFirstName(updateUser, "John");
		userService.updateLastName(updateUser, "Snow");
		userService.updateUsername(updateUser);
		assertEquals("josn01", updateUser.getUsername());
	}
	
	@Test
	public void inactivatingUserUpdatedsWorkitems() throws ServiceException {
		workItemService.addUserToWorkItem(createdWorkitem, updateUser);
		workItemService.updateStatus(createdWorkitem, Status.STARTED);
		userService.updateStatusInactive(updateUser);
		assertEquals(Status.UNSTARTED, workItemService.refreshEntity(createdWorkitem).getStatus());
	}

}
