package se.jdr.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import se.jdr.AbstractTest;

import static org.junit.Assert.assertTrue;

public final class WorkItemRepositoryTest extends AbstractTest {

    @Autowired
    WorkItemRepository workItemRepository;

    @Test
    public void placeHolder() {
    	assertTrue(true);
    }
}
