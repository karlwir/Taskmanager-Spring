package se.jdr.service;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.jdr.AbstractTest;
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
    public void placeHolder() {
    	assertTrue(true);
    }

}
