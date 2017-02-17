package se.jdr.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import se.jdr.model.AbstractEntity;

@Component
public class ServiceTransaction {

	@Transactional
	public <E extends AbstractEntity> E execute(Action<E> action) throws ServiceException {
		return action.action();
	}

	@FunctionalInterface
	public static interface Action<E extends AbstractEntity> {
		E action() throws ServiceException;

	}

}
