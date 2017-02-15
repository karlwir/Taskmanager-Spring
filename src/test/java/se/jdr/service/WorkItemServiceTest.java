package se.jdr.service;

import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import se.jdr.AbstractTest;
import se.jdr.config.TestConfig;
import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.repository.IssueRepository;
import se.jdr.repository.WorkItemRepository;

public class WorkItemServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	public WorkItemService workItemService;
	
	@MockBean
	WorkItemRepository workItemRepository;
	@MockBean
	UserService userService;
	@MockBean
	IssueRepository issueRepository;
	@MockBean
	ServiceTransaction transaction;
	@MockBean
	User user;
	@MockBean
	WorkItem workItem;

	@Test
	public void canOnlyAssignWorkItemToActiveUser() throws ServiceException {

		when(user.isActiveUser()).thenReturn(false);
		when(userService.addOrUpdateUser(user)).thenReturn(user);
		
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("user must be active and cannot have more than 5 work items");
		
		workItemService.addUserToWorkItem(workItem, user);;
	}

}
