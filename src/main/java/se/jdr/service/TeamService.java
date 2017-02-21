package se.jdr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.repository.TeamRepository;

@Component
public final class TeamService extends BaseService<Team, TeamRepository> {

	@Autowired
	public TeamService(TeamRepository tr, ServiceTransaction st) {
		super(tr, st);
	}

	public Team createTeam(String teamName) throws ServiceException {
		return execute(() -> repository.save(new Team(teamName)));
	}

	public Team updateStatusActive(Team team) throws ServiceException {
		return execute(() -> {
			team.setActiveTeam(true);
			return repository.save(team);
		});
	}

	public Team updateStatusInactive(Team team) throws ServiceException {
		return execute(() -> {
			team.setActiveTeam(false);
			return repository.save(team);
		});
	}

	public Team updateName(Team team, String teamName) throws ServiceException {
		return execute(() -> {
			team.setTeamName(teamName);
			return repository.save(team);
		});
	}

	public Team addUserToTeam(User user, Team team) throws ServiceException {
		if (isValidTeamSize(team)) {
			return execute(() -> {
				userService.updateTeam(user, team);
				return team;
			});
		} else {
			throw new ServiceException("Team is full!");
		}
	}

	private boolean isValidTeamSize(Team team) throws ServiceException {
		return execute(() -> userService.getUsersByTeamId(team.getId()).size() < 10);
	}

}
