package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.User;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.UserRepository;

@Component
public class UserService {

	private final UserRepository userRepository;
	private final ServiceManager serviceManager;
	private final ServiceTransaction transaction;

	@Autowired
	public UserService(UserRepository userRepository, ServiceManager serviceManager, ServiceTransaction transaction) {
		this.userRepository = userRepository;
		this.serviceManager = serviceManager;
		this.transaction = transaction;
	}

	public User addOrUpdateUser(User user) {
		return userRepository.save(user);
	}
	
	public User updateFirstName(User user, String firstname) throws ServiceException {
		return user.getUpdater(this).setFirstName(firstname);
	}
	
	public User updateLastName(User user, String lastname) throws ServiceException {
		return user.getUpdater(this).setLastName(lastname);
	}

	public User updateUsername(User user, String username) throws ServiceException {
		if (username.length() >= 10) {
			return user.getUpdater(this).setUsername(username);
		} else {
			throw new ServiceException("Username is too short!");
		}
	}

	public User updateUserStatus(User user, boolean status) throws ServiceException {
		if (!status) {
			return transaction.execute(() -> {
				serviceManager.getWorkItemService().getAllWorkItemsByUser(user).forEach(wi -> {
					wi.getUpdater(serviceManager.getWorkItemService()).setStatus(Status.UNSTARTED);
				});
				return user.getUpdater(this).setActiveUser(status);
			});
		} else {
			return user.getUpdater(this).setActiveUser(status);				
		}
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
