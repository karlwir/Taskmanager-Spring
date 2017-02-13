package se.jdr.service;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import se.jdr.AbstractTest;
import se.jdr.model.WorkItem;

@Transactional
public class WorkItemServiceTest extends AbstractTest {

	@Autowired
	public WorkItemService workItemService;
	public UserService userService;

	@Before
	public void setup() {

	}

	@Test
	public void canAddOrUpdateWorkItem() {
		WorkItem workitem = workItemService.addOrUpdateWorkItem(new WorkItem("broken windows", "replace the windows"));

		fail("Not yet implemented");
	}

}
