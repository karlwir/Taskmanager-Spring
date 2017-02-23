package se.jdr.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import se.jdr.AbstractTest;

public class TeamRepositoryTest extends AbstractTest{

	@Autowired
	TeamRepository teamRepository;

	@Test
	public void placeHolder() {
		assertTrue(true);
	}

}
