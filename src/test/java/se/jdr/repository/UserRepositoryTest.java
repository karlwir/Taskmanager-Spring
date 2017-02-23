package se.jdr.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import se.jdr.AbstractTest;

public class UserRepositoryTest extends AbstractTest {

    @Autowired
    UserRepository userRepository;

	@Test
	public void placeHolder() {
		assertTrue(true);
	}
}