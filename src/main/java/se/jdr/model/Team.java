package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false, unique = true)
	private String teamName;
	@Column(nullable = false)
	private boolean activeTeam;

	protected Team() {
	}

	public Team(String teamName, boolean activeTeam) {
		this.teamName = teamName;
		this.activeTeam = activeTeam;
	}

	public Long getId() {
		return id;
	}

	public String getTeamName() {
		return teamName;
	}

	public boolean isActiveTeam() {
		return activeTeam;
	}

	@Override
	public String toString() {
		return "Team id: " + id + ", team name: " + teamName + ", active: " + activeTeam;
	}

}
