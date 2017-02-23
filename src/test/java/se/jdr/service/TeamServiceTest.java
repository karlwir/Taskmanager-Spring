package se.jdr.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.jdr.AbstractTest;
import se.jdr.repository.TeamRepository;
import se.jdr.repository.UserRepository;


public class TeamServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	TeamService teamService;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	TeamRepository teamRepository;
	
	@Test
    public void placeHolder() {
    	assertTrue(true);
    }
}
