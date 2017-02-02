package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	private String lastname;
	@Column(nullable = false)
	private boolean activeUser;
	private long teamId;

	protected User() {
	}

	public User(String username, String firstname, String lastname, boolean activeUser, long teamId) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.activeUser = activeUser;
		this.teamId = teamId;
	}

	public Long getId() {
		return id;
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

	public long getTeamId() {
		return teamId;
	}

	@Override
	public String toString() {
		return "User " + id + ", username: " + username + ", firstname: " + firstname + ", lastname: " + lastname
				+ ", active: " + activeUser + ", teamId: " + teamId;
	}

}
