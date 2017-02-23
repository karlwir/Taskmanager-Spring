package se.jdr.repository;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import se.jdr.AbstractTest;

public class IssueRepositoryTest extends AbstractTest {

	@Autowired
	IssueRepository issueRepository;

	@Test
	public void placeHolder() {
		assertTrue(true);
	}
}
