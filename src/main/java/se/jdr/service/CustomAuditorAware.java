package se.jdr.service;

import org.springframework.data.domain.AuditorAware;

public class CustomAuditorAware implements AuditorAware<String> {

	private String user;

	public CustomAuditorAware(String user) {
		this.user = user;
	}

	@Override
	public String getCurrentAuditor() {
		return user;
	}

}
