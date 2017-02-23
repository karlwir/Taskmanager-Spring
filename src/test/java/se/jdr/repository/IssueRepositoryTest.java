package se.jdr.repository;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.jdr.AbstractTest;

public class IssueRepositoryTest extends AbstractTest {

	@MockBean
	IssueRepository issueRepository;

	@Test
	public void placeHolder() {
		assertTrue(true);
	}
}
