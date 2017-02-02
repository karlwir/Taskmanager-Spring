package se.jdr.model;

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

	private String teamName;

	protected Team() {
	}

	public Team(String teamName) {
		this.teamName = teamName;
	}

	public Long getId() {
		return id;
	}

	public String getTeamName() {
		return teamName;
	}

	@Override
	public String toString() {
		return "Team id: " + id + ", teamName: " + teamName;
	}

}
