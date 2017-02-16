package se.jdr.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import se.jdr.model.User.UserUpdater;
import se.jdr.service.TeamService;
import se.jdr.service.UserService;

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

	public String getTeamName() {
		return teamName;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public boolean isActiveTeam() {
		return activeTeam;
	}

	@Override
	public String toString() {
		return "Team id: " + getId() + ", team name: " + teamName + ", active: " + activeTeam;
	}
	
	public TeamUpdater getUpdater(TeamService teamService) {
		return new TeamUpdater(this, teamService);
	}

	public class TeamUpdater {

		private Team team;
		private TeamService teamService;

		private TeamUpdater(Team team, TeamService teamService) {
			this.team = team;
			this.teamService = teamService;
		}

		public Team setTeamName(String teamName) {
			team.teamName = teamName;
			return teamService.addOrUpdateTeam(team);
		}
		
		public Team setActiveTeam(boolean activeTeam) {
			team.activeTeam = activeTeam;
			return teamService.addOrUpdateTeam(team);
		}

		public void setUsers(Collection<User> users) {
			team.users = users;
		}

	}

}
