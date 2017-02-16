package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.repository.TeamRepository;

@Component
public final class TeamService {

	private final TeamRepository teamRepository;
	private final ServiceManager serviceManager;

	@Autowired
	public TeamService(TeamRepository teamRepository, ServiceManager serviceManager) {
		this.teamRepository = teamRepository;
		this.serviceManager = serviceManager;
	}

	public Team addOrUpdateTeam(Team team) {
		return teamRepository.save(team);
	}

	public Team updateTeamStatus(Team team, boolean status) {
		return team.getUpdater(this).setActiveTeam(status);
	}
	
	public Team updateTeamName(Team team, String teamName) {
		return team.getUpdater(this).setTeamName(teamName);
	}

	public Collection<Team> getAllTeams() {
		return (Collection<Team>) teamRepository.findAll();
	}

	public Team addUserToTeam(User user, Team team) throws ServiceException {
		if (isValidTeamSize(team)) {
			user.getUpdater(serviceManager.getUserService()).setTeam(team);
			return team;
		} else {
			throw new ServiceException("Team is full!");
		}
	}

	private boolean isValidTeamSize(Team team) {
		return serviceManager.getUserService().getUsersByTeamId(team.getId()).size() < 10;
	}

}
