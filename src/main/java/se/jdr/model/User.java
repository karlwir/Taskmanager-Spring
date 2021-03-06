package se.jdr.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Audited(withModifiedFlag=true)
@Table(name = "users")
public class User extends AbstractEntity {

	@Column
	private String userId;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String firstname;
	
	@Column(nullable = false)
	private String lastname;

	@Column(nullable = false)
	private String passwordHash;
	
	@Column(nullable = false)
	private boolean activeUser;
	
	@ManyToOne
	@NotAudited
	private Team team;
	
	@OneToMany(mappedBy = "user")
	@NotAudited
	private Collection<WorkItem> workitems;

	@LastModifiedDate
	protected LocalDateTime revisionDate;
	@LastModifiedBy
	@OneToOne
	protected User revisionBy;
	
	protected User() {
	}

	public User(String username, String firstname, String lastname, String passwordHash) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.passwordHash = passwordHash;
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
	
	public String getPasswordHash() {
		return passwordHash;
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

	private boolean hasCreatedBy(){
		return createdBy != null;
	}
	
	private boolean hasRevisionBy() {
		return revisionBy != null;
	}

	@Override
	public String toString() {
		String createdBy = hasCreatedBy() ? this.createdBy.getUsername() : null;
		String revisionBy = hasRevisionBy() ? this.revisionBy.getUsername() : null;
		return "User, username: " + username 
					+ ", firstname: " + firstname 
					+ ", lastname: " + lastname 
					+ ", activeUser: " + activeUser 
					+ ", team: " + team 
					+ ", createdBy: " + createdBy + " - " + createdDate 
					+ ", revisionBy: " + revisionBy + " - " + revisionDate;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof User) {
			User otherUser = (User) other;
			return this.getId() == otherUser.getId();
		}
		return false;
	}

}
