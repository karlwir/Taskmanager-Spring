package se.jdr.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class Team extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String teamName;
	@Column(nullable = false)
	private boolean activeTeam;
	@OneToMany(mappedBy = "team")
	Collection<User> users;

	protected Team() {
	}

	public Team(String teamName, boolean activeTeam) {
		this.teamName = teamName;
		this.activeTeam = activeTeam;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public void setActiveTeam(boolean activeTeam) {
		this.activeTeam = activeTeam;
	}

	public String getTeamName() {
		return teamName;
	}

	public boolean isActiveTeam() {
		return activeTeam;
	}

	@Override
	public String toString() {
		return "Team id: " + getId() + ", team name: " + teamName + ", active: " + activeTeam;
	}

}
