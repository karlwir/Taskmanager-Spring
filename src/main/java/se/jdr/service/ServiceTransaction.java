package se.jdr.service;

import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class ServiceTransaction {

	@Transactional
	public <T> T execute(Supplier<T> action) {
		return action.get();
	}
}
