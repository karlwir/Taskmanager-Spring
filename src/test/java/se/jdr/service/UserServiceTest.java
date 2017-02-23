package se.jdr.service;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import se.jdr.AbstractTest;

public class UserServiceTest extends AbstractTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
	UserService userService;

    @Test
    public void placeHolder() {
    	assertTrue(true);
    }

}
