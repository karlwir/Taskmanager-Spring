package se.jdr.service;

import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.jdr.AbstractTest;
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
		expectedException.expectMessage("Invalid workitem status");
		
		issueService.createIssue(workItem, "IssueDescription");
	}
	
}
