package se.jdr.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import se.jdr.config.TestConfig;
import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.service.ServiceException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserRepositoryTest {

    @MockBean
    UserRepository userRepository;

    private String userName = "Robbe";
    private String firstName = "Robert";
    private String lastName = "Savela";
    private User user = new User(userName, firstName, lastName);

    @Test
    public void canGetUser() throws ServiceException {
        when(userRepository.findUserByUserId("15")).thenReturn(user);
        User userfromDb = userRepository.findUserByUserId("15");
        System.out.println(user);
        assertThat(userfromDb, is(user));
    }

    @Test
    public void canGetUserByFirstName() {
        when(userRepository.getUser(firstName, "%", "%")).thenReturn(Stream.of(user).collect(Collectors.toList()));
        List<User> users = userRepository.getUser(firstName, "%", "%").stream().collect(Collectors.toList());
        User userFromDb = users.get(0);
        assertThat(user, is(userFromDb));


    }

    @Test
    public void canGetUserByLastName() {
        when(userRepository.getUser("%", lastName, "%")).thenReturn(Stream.of(user).collect(Collectors.toList()));
        List<User> users = userRepository.getUser("%", lastName, "%").stream().collect(Collectors.toList());
        User userFromDb = users.get(0);
        assertThat(userFromDb, is(user));
    }

    @Test
    public void canGetUserByUsername() {
        when(userRepository.getUser("%", "%", userName)).thenReturn(Stream.of(user).collect(Collectors.toList()));
        List<User> users = userRepository.getUser("%", "%", userName).stream().collect(Collectors.toList());
        User userFromDb = users.get(0);
        assertThat(userFromDb, is(user));
    }

//    @Test
//    public void canGetUserInTeam() {
//        Team team = new Team("Team1", true);
//        team.setUsers(Stream.of(user).collect(Collectors.toList()));
//        when(userRepository.findByTeamId(team.getId())).thenReturn(Stream.of(user).collect(Collectors.toList()));
//        List<User> users = userRepository.findByTeamId(team.getId()).stream().collect(Collectors.toList());
//        User userFromDb = users.get(0);
//        assertThat(userFromDb, is(user));
//
//    }
//
//
//    @Test
//    public void CanCountUsersByTeamId() {
//        Team team = new Team("Team1", true);
//        team.setUsers(Stream.of(user).collect(Collectors.toList()));
//        when(userRepository.countByTeamId(team.getId())).thenReturn(1L);
//        Long size = userRepository.countByTeamId(team.getId());
//        assertThat(size, is(1L));
//    }

}