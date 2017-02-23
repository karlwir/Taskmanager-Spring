package se.jdr.repository;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.jdr.AbstractTest;

import static org.junit.Assert.assertTrue;

public final class WorkItemRepositoryTest extends AbstractTest {

    @MockBean
    WorkItemRepository workItemRepository;

    @Test
    public void placeHolder() {
    	assertTrue(true);
    }
}
