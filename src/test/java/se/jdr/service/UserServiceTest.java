package se.jdr.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import se.jdr.AbstractTest;
import se.jdr.config.TestConfig;
import se.jdr.model.User;
import se.jdr.repository.UserRepository;

public class UserServiceTest extends AbstractTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
	UserService userService;

	@Test
	public void shouldThrowExceptionWhenUsernameTooShort() throws ServiceException {
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("Username is too short!");
		userService.createUser("Robert", "Savela", "password");
	}

}
