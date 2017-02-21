package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

	public User createUser(String username, String firstname, String lastname) throws ServiceException {
		if (isValidUsername(username)) {
			return super.execute(() -> repository.save(new User(username, firstname, lastname)));
		} else {
			throw new ServiceException("Username is too short!");
		}
	}

	public User updateFirstName(User user, String firstname) throws ServiceException {
		return super.execute(() -> {
			user.setFirstName(firstname);
			return repository.save(user);
		});
	}

	public User updateFirstName(Long id, String firstname) throws ServiceException {
		return super.execute(() -> {
			return repository.save(repository.findOne(id).setFirstName(firstname));
		});
	}

	public User updateLastName(User user, String lastname) throws ServiceException {
		return super.execute(() -> {
			user.setLastName(lastname);
			return repository.save(user);
		});
	}

	public User updateUsername(User user, String username) throws ServiceException {
		if (isValidUsername(username)) {
			return super.execute(() -> {
				user.setUsername(username);
				return repository.save(user);
			});
		} else {
			throw new ServiceException("Username is too short!");
		}
	}

	public User updateStatusInactive(User user) throws ServiceException {
		return super.transaction(() -> {
			for (WorkItem workitem : workItemService.getWorkItemsByUser(user)) {
				workItemService.updateStatus(workitem, Status.UNSTARTED);
			}
			user.setActiveUser(false);
			return repository.save(user);
		});
	}

	public User updateStatusActive(User user) throws ServiceException {
		return super.execute(() -> {
			user.setActiveUser(true);
			return repository.save(user);
		});
	}

	public User updateTeam(User user, Team team) throws ServiceException {
		return super.execute(() -> {
			user.setTeam(team);
			return repository.save(user);
		});
	}

	public Collection<User> getUsersByTeamId(Long teamId) throws ServiceException {
		return super.execute(() -> repository.findByTeamId(teamId));
	}

	public User getUserByUserId(String userId) throws ServiceException {
		return super.execute(() -> repository.findUserByUserId(userId));
	}

	public Collection<User> getUser(String firstname, String lastname, String username) throws ServiceException {
		return super.execute(() -> repository.getUser(firstname, lastname, username));
	}

	public Collection<User> getUserByFirstname(String firstname) throws ServiceException {
		return getUser(firstname, "%", "%");
	}

	public Collection<User> getUserByLastname(String lastname) throws ServiceException {
		return getUser("%", lastname, "%");
	}

	public Collection<User> getUserByUsername(String username) throws ServiceException {
		return getUser("%", "%", username);
	}

	private static boolean isValidUsername(String username) {
		return username.length() >= 10;
	}

}
