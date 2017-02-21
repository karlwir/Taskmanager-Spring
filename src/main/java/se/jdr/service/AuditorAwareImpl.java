package se.jdr.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	private String user;

	public AuditorAwareImpl() {
		this.user = "Kalle";
	}

	@Override
	public String getCurrentAuditor() {
		return user;
	}

}
