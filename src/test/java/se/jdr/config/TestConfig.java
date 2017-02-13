package se.jdr.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import se.jdr.repository.IssueRepository;
import se.jdr.service.IssueService;

@TestConfiguration
public class TestConfig {

	@Bean
	IssueRepository issueRepository() {
		return new IssueRepository();
	}

	@Bean
	IssueService issueService() {
		return new IssueService();
	}
}
