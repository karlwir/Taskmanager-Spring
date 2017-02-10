package se.jdr.service;

import se.jdr.model.User;
import se.jdr.config.TestConfig;
import se.jdr.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
    
	@MockBean
	private User user;

	@Test
	public void canGetUserByUserId() {
		given(userRepository.findUserByUserId("15")).willReturn(user);
		User userFromService = userService.getUserByUserId("15");
		assertEquals(userFromService, user);
	}

}
