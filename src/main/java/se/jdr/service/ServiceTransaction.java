package se.jdr.service;

import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import se.jdr.model.AbstractEntity;

@Component
public class ServiceTransaction {

	@Transactional
	public <E extends AbstractEntity> E execute(Supplier<E> action) {
		return action.get();
	}
}
