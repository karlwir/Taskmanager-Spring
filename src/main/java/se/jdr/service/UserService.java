package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.UserRepository;

@Component
public class UserService extends BaseService<User, UserRepository> {

	@Autowired
	public UserService(UserRepository ur, ServiceTransaction st) {
		super(ur, st);
	}

	public User createUser(String firstname, String lastname, String password) throws ServiceException {
			return execute(() -> repository.save(new User(generateUsername(firstname, lastname), firstname, lastname, generateHash(password))));
	}

	public User updateFirstName(User user, String firstname) throws ServiceException {
		return execute(() -> {
			user.setFirstName(firstname);
			return repository.save(user);
		});
	}

	public User updateLastName(User user, String lastname) throws ServiceException {
		return execute(() -> {
			user.setLastName(lastname);
			return repository.save(user);
		});
	}

	public User updateUsername(User user) throws ServiceException {
			return execute(() -> {
				user.setUsername(generateUsername(user.getFirstname(), user.getLastname()));
				return repository.save(user);
			});
	}

	public User updateStatusInactive(User user) throws ServiceException {
		return transaction(() -> {
			for (WorkItem workitem : workItemService.getByUser(user)) {
				workItemService.updateStatus(workitem, Status.UNSTARTED);
			}
			user.setActiveUser(false);
			return repository.save(user);
		});
	}

	public User updateStatusActive(User user) throws ServiceException {
		return execute(() -> {
			user.setActiveUser(true);
			return repository.save(user);
		});
	}

	public User updateTeam(User user, Team team) throws ServiceException {
		return execute(() -> {
			user.setTeam(team);
			return repository.save(user);
		});
	}

	public Collection<User> getUsersByTeamId(Long teamId) throws ServiceException {
		return execute(() -> repository.findByTeamId(teamId));
	}

	public User getUserByUserId(String userId) throws ServiceException {
		return execute(() -> repository.findUserByUserId(userId));
	}

	public Collection<User> getUser(String firstname, String lastname, String username) throws ServiceException {
		return execute(() -> repository.getUser(firstname, lastname, username));
	}

	public Collection<User> getByFirstname(String firstname) throws ServiceException {
		return getUser(firstname, "%", "%");
	}

	public Collection<User> getByLastname(String lastname) throws ServiceException {
		return getUser("%", lastname, "%");
	}

	public User getByUsername(String username) throws ServiceException {
		Collection<User> usersByUsername = getUser("%", "%", username);
		return (usersByUsername.iterator().hasNext()) ? usersByUsername.iterator().next() : null;
	}

	private static boolean isValidUsername(String username) {
		return username.length() >= 6;
	}

	private String generateHash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	private String generateUsername(String firstName, String lastName) throws ServiceException {
		String userNameFirst = firstName.substring(0, 2).toLowerCase();
		String userNameSecond = lastName.substring(0, 2).toLowerCase();
		String userName = null;

		for (Integer i = 1; i <= 100; i++) {
			if (i < 10) {
				userName = userNameFirst + userNameSecond + "0" + i.toString();

				if (getByUsername(userName) == null) {
					break;
				} else {
					continue;
				}
			} else if (i < 100) {
				userName = userNameFirst + userNameSecond + i.toString();

				if (getByUsername(userName) == null) {
					break;
				} else {
					continue;
				}
			} else {
				throw new ServiceException("No available username for : " + firstName + lastName);
			}
		}

		return userName;
	}

}
