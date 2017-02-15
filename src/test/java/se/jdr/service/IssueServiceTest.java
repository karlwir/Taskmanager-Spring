package se.jdr.service;

import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import se.jdr.AbstractTest;
import se.jdr.config.TestConfig;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.IssueRepository;
import se.jdr.repository.WorkItemRepository;

public class IssueServiceTest extends AbstractTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	IssueService issueService;
	
	@MockBean
	IssueRepository issueRepository;
	@MockBean
	WorkItemRepository workItemRepository;
	@MockBean
	WorkItem workItem;
	
	@Test
	public void canOnlyAddIssueToDoneWorkItem() throws ServiceException {
		when(workItem.getStatus()).thenReturn(Status.STARTED);
		
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("Invalid work item status");
		
		issueService.addIssue(workItem, "IssueDescription");
	}
	
}
