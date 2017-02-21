package se.jdr.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class CustomAuditorAware implements AuditorAware<String> {

	private String user;

	public CustomAuditorAware() {
		this.user = "Kalle";
	}

	@Override
	public String getCurrentAuditor() {
		return user;
	}

}
