package se.jdr.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.repository.TeamRepository;
import se.jdr.repository.UserRepository;

@Component
public final class TeamService {

	private final TeamRepository teamRepository;
	private final UserRepository userRepository;

	@Autowired
	public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
		this.teamRepository = teamRepository;
		this.userRepository = userRepository;
	}

	public Team addOrUpdateTeam(Team team) {
		return teamRepository.save(team);
	}

	public Team updateTeamStatus(Team team, boolean status) {
		team.setActiveTeam(status);
		return addOrUpdateTeam(team);
	}

	public Collection<Team> getAllTeams() {
		List<Team> teams = new ArrayList<>();
		teamRepository.findAll().forEach(t -> teams.add(t));
		return teams;
	}

	public void addUserToTeam(User user, Team team) throws ServiceException {
		Team teamToDB = addOrUpdateTeam(team);

		if (isValidTeamSize(teamToDB)) {
			user.setTeam(teamToDB);
			userRepository.save(user);
		} else {
			throw new ServiceException("Team is full!");
		}
	}

	public boolean isValidTeamSize(Team team) {
		return userRepository.countByTeamId(team.getId()) < 10;
	}

}
