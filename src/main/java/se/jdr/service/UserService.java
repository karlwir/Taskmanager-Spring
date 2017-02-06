package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.User;
import se.jdr.repository.UserRepository;

@Component
public final class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User addOrUpdateUser(User user) {
		return userRepository.save(user);
	}

	public User updateUsername(User user, String username) {
		user.setUsername(username);
		return addOrUpdateUser(user);
	}

	public User updateUserStatus(User user, boolean status) {
		user.setActiveUser(status);
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
