package se.jdr.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import se.jdr.service.UserService;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	@Column
	@GeneratedValue
	private String userId;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	private String lastname;
	@Column(nullable = false)
	private boolean activeUser;
	@ManyToOne
	private Team team;
	@OneToMany(mappedBy = "user")
	private Collection<WorkItem> workitems;

	protected User() {
	}

	public User(String username, String firstname, String lastname) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.activeUser = true;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public boolean isActiveUser() {
		return activeUser;
	}

	public Team getTeam() {
		return team;
	}

	public String getUserId() {
		return userId;
	}
	
	@Override
	public String toString() {
		return "User " + getId() + ", username: " + username + ", firstname: " + firstname + ", lastname: " + lastname
				+ "userId : " + userId + ", active: " + activeUser + ", teamId: " + team;
	}

	public UserUpdater getUpdater(UserService userService) {
		return new UserUpdater(this, userService);
	}

	public class UserUpdater {

		private User user;
		private UserService userService;

		private UserUpdater(User user, UserService userService) {
			this.user = user;
			this.userService = userService;
		}

		public User setFirstName(String firstname) {
			user.firstname = firstname;
			return userService.addOrUpdateUser(user);
		}

		public User setLastName(String lastname) {
			user.lastname = lastname;
			return userService.addOrUpdateUser(user);
		}

		public User setUsername(String username) {
			user.username = username;
			return userService.addOrUpdateUser(user);
		}

		public User setActiveUser(boolean activeUser) {
			user.activeUser = activeUser;
			return userService.addOrUpdateUser(user);
		}

		public User setTeam(Team team) {
			user.team = team;
			return userService.addOrUpdateUser(user);
		}

	}

}
