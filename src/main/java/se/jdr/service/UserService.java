package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.User;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.UserRepository;
import se.jdr.repository.WorkItemRepository;

@Component
public final class UserService {

	private final UserRepository userRepository;
	private final WorkItemRepository workItemRepository;

	@Autowired
	public UserService(UserRepository userRepository, WorkItemRepository workItemRepository) {
		this.userRepository = userRepository;
		this.workItemRepository = workItemRepository;
	}

	public User addOrUpdateUser(User user) throws ServiceException {
		if (user.getUsername().length() >= 10) {
			return userRepository.save(user);
		} else {
			throw new ServiceException("Username is too short!");
		}
	}

	public User updateUsername(User user, String username) throws ServiceException {
		user.setUsername(username);
		return addOrUpdateUser(user);
	}

	public User updateUserStatus(User user, boolean status) throws ServiceException {
		user.setActiveUser(status);
		if (user.isActiveUser() == false) {
			workItemRepository.findByUserId(user.getId()).forEach(wi -> {
				wi.setStatus(Status.UNSTARTED);
				workItemRepository.save(wi);
			});
		}
		return addOrUpdateUser(user);
	}

	public Collection<User> getUsersByTeamId(Long teamId) {
		return userRepository.findByTeamId(teamId);
	}

	public User getUserByUserId(String userId) {
		return userRepository.findUserByUserId(userId);
	}

	public Collection<User> getUser(String firstname, String lastname, String username) {
		return userRepository.getUser(firstname, lastname, username);
	}

	public Collection<User> getUserByFirstname(String firstname) {
		return userRepository.getUser(firstname, "%", "%");
	}

	public Collection<User> getUserByLastname(String lastname) {
		return userRepository.getUser("%", lastname, "%");
	}

	public Collection<User> getUserByUsername(String username) {
		return userRepository.getUser("%", "%", username);
	}

}
