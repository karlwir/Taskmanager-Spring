package se.jdr.config;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@TestConfiguration
public class TestConfig {

	@Bean
	DataSource dataSource() {
		HikariConfig cfg = new HikariConfig();
		cfg.setDriverClassName("org.h2.Driver");
		cfg.setJdbcUrl("jdbc:h2:mem:;MODE=MySQL");
		return new HikariDataSource(cfg);
	}

}
