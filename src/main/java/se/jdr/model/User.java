package se.jdr.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
				+ ", userId : " + userId + ", active: " + activeUser + ", teamId: " + team;
	}

	public User setFirstName(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public User setLastName(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public User setActiveUser(boolean activeUser) {
		this.activeUser = activeUser;
		return this;
	}

	public User setTeam(Team team) {
		this.team = team;
		return this;
	}

}
