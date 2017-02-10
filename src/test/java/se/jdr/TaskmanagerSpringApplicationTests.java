package se.jdr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import se.jdr.config.TestConfig;
import se.jdr.model.User;
import se.jdr.repository.UserRepository;
import se.jdr.service.ServiceException;
import se.jdr.service.UserService;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class TaskmanagerSpringApplicationTests {

	@Autowired
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	User user;

	@Test
	public void contextLoads() throws ServiceException {
		given(userService.addOrUpdateUser(user)).willReturn(user);

	}

}
