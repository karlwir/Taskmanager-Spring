package se.jdr.service;

import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import se.jdr.model.AbstractEntity;
import se.jdr.model.User;
import se.jdr.service.ServiceTransaction.Action;

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
